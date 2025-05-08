package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobRequiredSkillDAO;
import com.biruk.habeshaJobs.DAO.SkillDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobRequiredSkillDTO;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class JobSkillService {

    private final JobRequiredSkillDAO jobRequiredSkillDAO;
    private final SkillService skillService;
    private final SkillDAO skillDAO;

    @Autowired
    public JobSkillService (JobRequiredSkillDAO jobRequiredSkillDAO, SkillService skillService, SkillDAO skillDAO){

        this.jobRequiredSkillDAO = jobRequiredSkillDAO;

        this.skillService = skillService;
        this.skillDAO = skillDAO;
    }

    //job is passed in from the job service layer when you’re creating a new job — so no need for the client to send job info.
    public JobRequiredSkill createJobRequiredSkill (IncomingJobRequiredSkillDTO incomingJobRequiredSkillDTO, Job job){

        Skill skill = skillService.getOrCreateSkill(incomingJobRequiredSkillDTO.getSkillName());

        JobRequiredSkill jobRequiredSkill = new JobRequiredSkill();
        jobRequiredSkill.setSkill(skill);
        jobRequiredSkill.setJob(job);

        return jobRequiredSkillDAO.save(jobRequiredSkill);
    }

    public JobRequiredSkill getJobRequiredSkill (UUID jrsId){

        return jobRequiredSkillDAO.findById(jrsId).orElseThrow(() ->
                new NoSuchElementException("The job required skill with Id : " + jrsId + " not Found"));

    }
    public List<JobRequiredSkill> getAllSkillsByJobId(UUID jobId) {
        List<JobRequiredSkill> skills = jobRequiredSkillDAO.findByJob_JobId(jobId);

        if (skills.isEmpty()) {
            throw new NoSuchElementException("No skill filled by employer with job ID " + jobId + " found");
        }

        return skills;
    }

    public void deleteJobRequiredSkill (UUID jrsId){

        if (!jobRequiredSkillDAO.existsById(jrsId)){
            throw new NoSuchElementException("jobRequiredSkill with ID "+ jrsId + " not found");
        }

        jobRequiredSkillDAO.deleteById(jrsId);
    }

    public JobRequiredSkill updateJobRequiredSkill (UUID jrsId, IncomingJobRequiredSkillDTO incomingJrsDTO) {

        Skill newSkill = skillService.getOrCreateSkill(incomingJrsDTO.getSkillName());

        JobRequiredSkill existingJrs = getJobRequiredSkill(jrsId);

        Job job = existingJrs.getJob();

        // Prevent assigning the same skill again
        boolean skillAlreadyAssigned = jobRequiredSkillDAO.findByJob_JobId(job.getJobId()).stream()
                .anyMatch(jrs ->
                        jrs.getSkill().getSkillId().equals(newSkill.getSkillId())
                                && !jrs.getSkillsRequiredId().equals(jrsId) // not the one being updated
                );

        if (skillAlreadyAssigned) {
            throw new IllegalStateException("This skill is already assigned to the job.");
        }

        existingJrs.setSkill(newSkill);
        return jobRequiredSkillDAO.save(existingJrs);
    }
}
