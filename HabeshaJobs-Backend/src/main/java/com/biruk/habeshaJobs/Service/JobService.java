package com.biruk.habeshaJobs.Service;


import com.biruk.habeshaJobs.DAO.EmployerDAO;
import com.biruk.habeshaJobs.DAO.JobDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobDTO;
import com.biruk.habeshaJobs.Model.Employer;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class JobService {

    private final JobDAO jobDAO;
    private final EmployerDAO employerDAO;
    private final ObjectMapper objectMapper;


    @Autowired
    public JobService (JobDAO jobDAO, EmployerDAO employerDAO, ObjectMapper objectMapper) {
        this.jobDAO = jobDAO;
        this.employerDAO = employerDAO;
        this.objectMapper = objectMapper;
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
        job.setApplicationDeadline(incomingJobDTO.getApplicationDeadline());
        job.setNumberOfOpenings(incomingJobDTO.getNumberOfOpenings());
        job.setEmployer(employer);

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

        Job job = jobDAO.findById(jobId).orElseThrow(() ->
                new NoSuchElementException("Job with ID " + jobId + " not found"));

        try {
            // Convert Job to JsonNode
            JsonNode originalNode = objectMapper.valueToTree(job);

            //Merge patchNode into originalNode
            JsonNode merged = objectMapper.readerForUpdating(originalNode).readValue(patchNode);

            //convert merged node back to Job
            Job updatedJob = objectMapper.treeToValue(merged, Job.class);

            // Ensure Id stays the same
            updatedJob.setJobId(job.getJobId());

            // Save and return the updated job
            return new OutgoingJobDTO(jobDAO.save(updatedJob));

        }catch (Exception e){
            throw new RuntimeException("Failed to apply patch: " + e.getMessage());

        }
    }









}
