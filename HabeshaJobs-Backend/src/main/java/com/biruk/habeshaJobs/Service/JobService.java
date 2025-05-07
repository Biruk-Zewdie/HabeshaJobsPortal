package com.biruk.habeshaJobs.Service;


import com.biruk.habeshaJobs.DAO.EmployerDAO;
import com.biruk.habeshaJobs.DAO.JobDAO;
import com.biruk.habeshaJobs.DAO.SkillDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobDTO;
import com.biruk.habeshaJobs.DTO.IncomingJobRequiredSkillDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobDTO;
import com.biruk.habeshaJobs.Model.Common.GeoHelper;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import com.biruk.habeshaJobs.Model.JobApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JobService {

    private final JobDAO jobDAO;
    private final JobSeekerService jobSeekerService;
    private final EmployerDAO employerDAO;
    private final SkillDAO skillDAO;
    private final ObjectMapper objectMapper;
    private final GeoHelper geoHelper;


    @Autowired
    public JobService (JobDAO jobDAO, JobSeekerService jobSeekerService, EmployerDAO employerDAO, SkillDAO skillDAO, ObjectMapper objectMapper, GeoHelper geoHelper) {
        this.jobDAO = jobDAO;
        this.jobSeekerService = jobSeekerService;
        this.employerDAO = employerDAO;
        this.skillDAO = skillDAO;
        this.objectMapper = objectMapper;
        this.geoHelper = geoHelper;
    }


    // Only logged in user can post a job
    // the employer field in the job object will be set to the logged in Employer.
    // We will extract the User ID form the JWT token and find the employer using the user Id and set the employer into the job object that is gonna be posted.
    public OutgoingJobDTO postJob (IncomingJobDTO incomingJobDTO, UUID userId){

        Employer employer = employerDAO.findEmployerByUserUserId(userId).orElseThrow( () ->
                new NoSuchElementException("User with ID " + userId + " not found"));


        Job job = new Job();
        job.setJobTitle(incomingJobDTO.getJobTitle());
        job.setSalary(incomingJobDTO.getSalary());
        job.setAddress(incomingJobDTO.getAddress());
        job.setJobType(incomingJobDTO.getJobType());
        job.setJobDescription(incomingJobDTO.getJobDescription());
//        job.setJobRequiredSkill(incomingJobDTO.getIncomingJobRequiredSkillDTOs());
        job.setApplicationDeadline(incomingJobDTO.getApplicationDeadline());
        job.setNumberOfOpenings(incomingJobDTO.getNumberOfOpenings());
        job.setEmployer(employer);

        // Set the location using the GeoHelper
        job.setLocation(geoHelper.createPointFromAddress(incomingJobDTO.getAddress()));

        List<JobRequiredSkill> jobRequiredSkills = new ArrayList<>();

        for (IncomingJobRequiredSkillDTO incomingJobRequiredSkillDTO: incomingJobDTO.getJobRequiredSkills()) {
            String skillName = incomingJobRequiredSkillDTO.getSkillName();

            //fetch or create skill

            Skill skill = skillDAO.findBySkillName(skillName).orElseGet(() -> {
                Skill newSkill = new Skill();
                newSkill.setSkillName(skillName);
                return skillDAO.save(newSkill);
            });

            // Create a job required skill
            JobRequiredSkill jobRequiredSkill = new JobRequiredSkill();
            jobRequiredSkill.setSkill(skill);
            jobRequiredSkill.setJob(job);       //set back reference for Job

            jobRequiredSkills.add(jobRequiredSkill);
        }

        job.setJobRequiredSkill(jobRequiredSkills);

        Job savedJob = jobDAO.save(job);

        return new OutgoingJobDTO(savedJob);
    }

    public List<OutgoingJobDTO> getAllJobs () {

        //fetch all jobs from the database
        List<Job> jobs = jobDAO.findAll();

        if (jobs.isEmpty()) {
            throw new NoSuchElementException("No jobs found");
        }

        //to convert the list of jobs to a list of OutgoingJobDTO, we need to create a new list of OutgoingJobDTO.
        List <OutgoingJobDTO> outgoingJobDTOs = new ArrayList<>();

        //iterate through the list of jobs and convert each job to OutgoingJobDTO using the constructor of OutgoingJobDTO and add it to the list we created above.
        for (Job job : jobs) {
            OutgoingJobDTO outgoingJobDTO = new OutgoingJobDTO(job);
            outgoingJobDTOs.add(outgoingJobDTO);
        }

        //return the list of OutgoingJobDTOs
        return outgoingJobDTOs;
    }

    //This method will fetch a job
    public OutgoingJobDTO getJobById (UUID jobId) {

        Job job = jobDAO.findById(jobId).orElseThrow(() ->
                new NoSuchElementException("Job with id " + jobId + " not found"));

        return new OutgoingJobDTO(job);
    }

    // This method will fetch all jobs posted by a given employer ID.
    public List<OutgoingJobDTO> getJobsByEmployerId (UUID employerId){

        List<Job> jobs = jobDAO.findByEmployer_EmployerId(employerId).orElseThrow(() ->
                new NoSuchElementException("No jobs posted by employer with employer ID " + employerId + " found"));

        List<OutgoingJobDTO> outgoingJobDTOs = new ArrayList<>();

        for (Job job : jobs) {
            OutgoingJobDTO outgoingJobDTO = new OutgoingJobDTO(job);
            outgoingJobDTOs.add(outgoingJobDTO);
        }

        return outgoingJobDTOs;
    }

    // This method will delete a job by a given job ID from the database.`
    public void deleteJob (UUID jobId) {
        if (!jobDAO.existsById(jobId)) {
            throw new NoSuchElementException("Job with ID " + jobId + " not found");
        }
        jobDAO.deleteById(jobId);
    }


    // this method will update a job in the database by using a given job ID.
    public OutgoingJobDTO updateJob (UUID jobId, JsonNode patchNode) {

        Job existingJob = jobDAO.findById(jobId).orElseThrow(() ->
                new NoSuchElementException("Job with ID " + jobId + " not found"));

        try {
            // Backup relationships that could break
            Employer employer = existingJob.getEmployer();
            List<JobApplication> applications = existingJob.getJobApplications();

            // Directly patch the existing Job object
            objectMapper.readerForUpdating(existingJob).readValue(patchNode);

            if (existingJob.getAddress() != null){
                existingJob.setLocation(geoHelper.createPointFromAddress(existingJob.getAddress()));
            }

            //update skills if provided
            if (patchNode.has("skillsRequired") && patchNode.get("skillsRequired").isArray()){
                List<JobRequiredSkill> updatedSkills = new ArrayList<>();

                existingJob.getJobRequiredSkills().clear();

                for (JsonNode skillNode : patchNode.get("skillsRequired")){
                    String skillName = skillNode.asText().trim();

                    Skill skill = skillDAO.findBySkillNameIgnoreCase(skillName).orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setSkillName(skillName);
                        return skillDAO.save(newSkill);
                    });

                    JobRequiredSkill jrs = new JobRequiredSkill();
                    jrs.setSkill(skill);
                    jrs.setJob(existingJob);  //link back to the job
                    updatedSkills.add(jrs);
                }

                existingJob.getJobRequiredSkills().addAll(updatedSkills);
            }



            // Restore important relationships if needed
            existingJob.setEmployer(employer);
            existingJob.setJobApplications(applications);

            // Save and return
            Job savedJob = jobDAO.save(existingJob);

            return new OutgoingJobDTO(savedJob);
        }catch (Exception e){
            throw new RuntimeException("Failed to apply patch: " + e.getMessage());

        }
    }

    // all the methods below are used to filter the job based on different criteria.

    public List<OutgoingJobDTO> searchJobsByJobTitle (String jobTitle) {

        List<Job> jobs = jobDAO.findByJobTitleContainingIgnoreCase(jobTitle);

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();
    }

    public List <OutgoingJobDTO> searchBySalaryRange (double min, double max) {

        List <Job> jobs = jobDAO.findBySalaryBetween(min, max);

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();
    }

    public List <OutgoingJobDTO> searchByJobType(Job.JobType jobType){

        List<Job> jobs = jobDAO.findByJobType(jobType);

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();
    }

    public List<OutgoingJobDTO> findJobsPostedInLastDays (int days){
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(days);
        List <Job> jobs = jobDAO.findByCreatedAtBetween(startDate, endDate);

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();
    }

    public List <OutgoingJobDTO> findNearByJobsForJobSeeker (UUID jobSeekerId, double radiusInMiles){

        Point jSeekerLocation = jobSeekerService.getJobSeekerLocation(jobSeekerId);

        if (jSeekerLocation == null){
            throw new IllegalStateException("jobSeeker location can not be null");
        }
        double jSeekerLat = jSeekerLocation.getY();
        double jSeekerLon = jSeekerLocation.getX();
        double radiusInMeters = radiusInMiles * 1609.34;      //convert miles to meters

        List<Job> jobs = jobDAO.findByNearbyJobs(jSeekerLat, jSeekerLon, radiusInMeters);

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();

        //return findNearByJobs(jSeekerLat, jSeekerLon, radiusInKM);


    }

    public List<OutgoingJobDTO> findJobsMatchingJobSeekerSkills (UUID jobSeekerId){

        List<Job> jobs = jobDAO.findMatchingJobsWithJobSeekerSkills(jobSeekerId);

        if (jobs.isEmpty()){
            throw new NoSuchElementException("No jobs found matching job seeker skills");
        }

        return jobs.stream().map(job -> new OutgoingJobDTO(job)).toList();
    }

    /*
    *Warning: the below 2 methods are deprecated and not used in the codebase. Commented out for reference purpose.
    * Why are they deprecated?
    * ST_DWithin already calculates distance geographically using PostGIS, just like haversine, but much faster and optimized via spatial indexing.
	* You’re filtering directly in the database, so:
	    1. You don’t load unnecessary job records into memory.
	    2. You avoid doing distance math in Java.
	    3. You improve performance for large datasets.
     */

//    public List<OutgoingJobDTO> findNearByJobs (double jSeekerLat, double jSeekerLon, double radiusInKM){
//
//        List<Job> allJobs = jobDAO.findAll();
//
//        return allJobs.stream().filter(job -> {
//            double jobLat = job.getLocation().getY();
//            double jobLon = job.getLocation().getX();
//            double distance = haversine (jSeekerLat, jSeekerLon, jobLat, jobLon);
//            return distance <= radiusInKM;
//        }).map(job -> new OutgoingJobDTO(job)).toList();
//    }
//
//    // Haversine formula to calculate the distance between two points on the Earth
//    private double haversine(double jSeekerLat, double jSeekerLon, double jobLat, double jobLon) {
//
//        final int EARTH_RADIUS_KM = 6371;
//        double latDistanceInRad = Math.toRadians(jobLat - jSeekerLat);
//        double lonDistanceInRad = Math.toRadians(jobLon - jSeekerLon);
//        double a = Math.sin(latDistanceInRad/2) * Math.sin(latDistanceInRad/2) +
//                Math.cos(Math.toRadians(jSeekerLat)) * Math.cos(Math.toRadians(jobLat)) *
//                        Math.sin(lonDistanceInRad/2) * Math.sin(lonDistanceInRad/2);
//        double angularDistance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//        return EARTH_RADIUS_KM * angularDistance;
//
//    }


}
