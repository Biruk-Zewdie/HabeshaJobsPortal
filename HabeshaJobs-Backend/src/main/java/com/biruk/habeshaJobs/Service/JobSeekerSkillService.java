package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobSeekerSkillDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobSeekerSkillsDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobSeekerSkillDTO;
import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public OutgoingJobSeekerSkillDTO createJobSeekerSkill (IncomingJobSeekerSkillsDTO incomingJssDTO, JobSeeker jobSeeker) {

        Skill skill = skillService.getOrCreateSkill(incomingJssDTO.getSkillName());

        JobSeekerSkill jobSeekerSkill = new JobSeekerSkill();
        jobSeekerSkill.setSkillLevel(incomingJssDTO.getSkillLevel());;
        jobSeekerSkill.setSkill(skill);
        jobSeekerSkill.setJobSeeker(jobSeeker);

        jobSeekerSkillDAO.save(jobSeekerSkill);

        return new OutgoingJobSeekerSkillDTO(jobSeekerSkill);

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

    public OutgoingJobSeekerSkillDTO updateJobSeekerSkill (UUID jssId, IncomingJobSeekerSkillsDTO incomingJssDTO) {

    JobSeekerSkill existingJsSkill = getJobSeekerSkill(jssId);

    if (!existingJsSkill.getSkill().getSkillName().equals(incomingJssDTO.getSkillName())){
        existingJsSkill.getSkill().setSkillName(incomingJssDTO.getSkillName());
    }
    if (existingJsSkill.getSkillLevel().equals(incomingJssDTO.getSkillLevel())){
        existingJsSkill.setSkillLevel(incomingJssDTO.getSkillLevel());
    }

    //we can't update the jobSeeker because we are creating
    //existing.setJobSeeker(newJss.getJobSeeker());


    return  new OutgoingJobSeekerSkillDTO(jobSeekerSkillDAO.save(existingJsSkill));

    }

    public List<OutgoingJobSeekerSkillDTO> getAllJobSeekerSkillsByJobSeekerId (UUID jobSeekerId){

        List<JobSeekerSkill> jobSeekerSkills = jobSeekerSkillDAO.findByJobSeeker_JobSeekerId(jobSeekerId);

        List<OutgoingJobSeekerSkillDTO> outgoingJobSeekerSkillDTOs = new ArrayList<>();
        for (JobSeekerSkill jobSeekerSkill : jobSeekerSkills){
           OutgoingJobSeekerSkillDTO outgoingJobSeekerSkillDTO = new OutgoingJobSeekerSkillDTO(jobSeekerSkill);
            outgoingJobSeekerSkillDTOs.add(outgoingJobSeekerSkillDTO);
        }

        return outgoingJobSeekerSkillDTOs;
    }

}
