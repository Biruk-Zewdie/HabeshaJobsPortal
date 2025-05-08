package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobSeekerSkillDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobSeekerSkillsDTO;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class JobSeekerSkillService {

    private final JobSeekerSkillDAO jobSeekerSkillDAO;
    private final SkillService skillService;

    @Autowired
    public JobSeekerSkillService (JobSeekerSkillDAO jobSeekerSkillDAO, SkillService skillService){
        this.jobSeekerSkillDAO = jobSeekerSkillDAO;
        this.skillService = skillService;
    }

    public JobSeekerSkill createJobSeekerSkill (IncomingJobSeekerSkillsDTO incomingJssDTO, JobSeeker jobSeeker) {

        Skill skill = skillService.getOrCreateSkill(incomingJssDTO.getSkillName());
        JobSeekerSkill jobSeekerSkill = new JobSeekerSkill();
        jobSeekerSkill.setSkillLevel(incomingJssDTO.getSkillLevel());
        jobSeekerSkill.setSkill(skill);
        jobSeekerSkill.setJobSeeker(jobSeeker);

        return jobSeekerSkillDAO.save(jobSeekerSkill);

    }

    public JobSeekerSkill getJobSeekerSkill (UUID jssId){

        return jobSeekerSkillDAO.findById(jssId).orElseThrow(() ->
                new NoSuchElementException("The job seeker skill with Id : "+ jssId + " not Found"));

    }

    public void deleteJobSeekerSkill (UUID jssId) {

        if(!jobSeekerSkillDAO.existsById(jssId)){
            throw new NoSuchElementException("jobSeekerSkill with ID "+ jssId + " not found");
        }
        jobSeekerSkillDAO.deleteById(jssId);
    }

    public JobSeekerSkill updateJobSeekerSkill (UUID jssId, JobSeekerSkill newJss) {

    JobSeekerSkill existingJsSkill = getJobSeekerSkill(jssId);

    if (!existingJsSkill.getSkill().equals(newJss.getSkill())){
        existingJsSkill.setSkill(newJss.getSkill());
    }
    if (existingJsSkill.getSkillLevel().equals(newJss.getSkillLevel())){
        existingJsSkill.setSkillLevel(newJss.getSkillLevel());
    }

    //we can't update the jobSeeker because we are creating
    //existing.setJobSeeker(newJss.getJobSeeker());

    return  jobSeekerSkillDAO.save(existingJsSkill);

    }

    public List<JobSeekerSkill> getAllJobSeekerSkillsByJobSeekerId (UUID jobSeekerId){

        return jobSeekerSkillDAO.findByJobSeeker_JobSeekerId(jobSeekerId);
    }

}
