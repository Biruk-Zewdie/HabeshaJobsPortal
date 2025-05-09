package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobSeekerSkillsDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobSeekerSkillDTO;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.Service.JobSeekerSkillService;
import com.biruk.habeshaJobs.Service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("jobSeekers/{jobSeekerId}/skill")
@CrossOrigin
public class JobSeekerSkillController {

    private final JobSeekerSkillService jobSeekerSkillService;
    private final JobSeekerDAO jobSeekerDAO;

    @Autowired
    public JobSeekerSkillController (JobSeekerSkillService jobSeekerSkillService, JobSeekerDAO jobSeekerDAO){
        this.jobSeekerSkillService = jobSeekerSkillService;
        this.jobSeekerDAO = jobSeekerDAO;

    }

    @PostMapping
    public ResponseEntity<OutgoingJobSeekerSkillDTO> createJobSeekerSkill (@RequestBody IncomingJobSeekerSkillsDTO incomingJobSeekerSkillsDTO, @PathVariable UUID jobSeekerId) {

        JobSeeker jobSeeker = jobSeekerDAO.findById(jobSeekerId).orElseThrow(() ->
                new NoSuchElementException("The jobSeeker with jobSeeker Id " + jobSeekerId + " not found"));

        OutgoingJobSeekerSkillDTO outgoingJobSeekerSkillDTO = jobSeekerSkillService.createJobSeekerSkill(incomingJobSeekerSkillsDTO, jobSeeker);

        return  ResponseEntity.ok(outgoingJobSeekerSkillDTO);

    }

    @GetMapping
    public ResponseEntity<List<OutgoingJobSeekerSkillDTO>> getAllJobSeekerSkillsByJobSeekerId (@PathVariable UUID jobSeekerId) {

        List<OutgoingJobSeekerSkillDTO> outgoingJobSeekerSkillDTOs = jobSeekerSkillService.getAllJobSeekerSkillsByJobSeekerId(jobSeekerId);

        return ResponseEntity.ok(outgoingJobSeekerSkillDTOs);

    }

    @DeleteMapping("/{jobSeekerSkillId}")
    public ResponseEntity<String> deleteJobSeekerSkill (@PathVariable UUID jobSeekerSkillId) {

        jobSeekerSkillService.deleteJobSeekerSkill(jobSeekerSkillId);

        return ResponseEntity.ok("Job seeker skill is Successfully deleted!");
    }

    @PatchMapping("/{jobSeekerSkillId}")
    public ResponseEntity<OutgoingJobSeekerSkillDTO> updateJobSeekerSkill (@PathVariable UUID jobSeekerSkillId, @RequestBody IncomingJobSeekerSkillsDTO incomingJssDTO){

       OutgoingJobSeekerSkillDTO outgoingJobSeekerSkillDTO =  jobSeekerSkillService.updateJobSeekerSkill(jobSeekerSkillId, incomingJssDTO);

       return ResponseEntity.ok(outgoingJobSeekerSkillDTO);

    }
}

