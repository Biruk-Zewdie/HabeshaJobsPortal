package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DAO.JobDAO;
import com.biruk.habeshaJobs.DTO.IncomingJobRequiredSkillDTO;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import com.biruk.habeshaJobs.Service.JobService;
import com.biruk.habeshaJobs.Service.JobSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("jobs/{jobId}/skills")
@CrossOrigin
public class JobRequiredSkillController {

    private final JobSkillService jobSkillService;
    private final JobDAO jobDAO;

    public JobRequiredSkillController(JobSkillService jobSkillService, JobDAO jobDAO){
        this.jobSkillService = jobSkillService;
        this.jobDAO = jobDAO;
    }

    @PostMapping
    public ResponseEntity<JobRequiredSkill> createJobRequiredSkill (@RequestBody IncomingJobRequiredSkillDTO incomingJRSDTO, @PathVariable UUID jobId){
        Job job = jobDAO.findById(jobId).orElseThrow(() ->
                new NoSuchElementException("Job with id " + jobId + " not found"));

        JobRequiredSkill jobRequiredSkill = jobSkillService.createJobRequiredSkill(incomingJRSDTO, job);

        return ResponseEntity.ok(jobRequiredSkill);
    }

    @GetMapping
    public ResponseEntity<List<JobRequiredSkill>> getSkillsRequiredForJob (@PathVariable UUID jobId){

        List<JobRequiredSkill> jobRequiredSkill = jobSkillService.getAllSkillsByJobId(jobId);

        return ResponseEntity.ok(jobRequiredSkill);
    }

    @PatchMapping("/{skillId}")
    public ResponseEntity<JobRequiredSkill> updateJobRequiredSkill (@PathVariable UUID skillId, @RequestBody IncomingJobRequiredSkillDTO incomingJRSDTO ){

        JobRequiredSkill jobRequiredSkill = jobSkillService.updateJobRequiredSkill(skillId, incomingJRSDTO);

        return ResponseEntity.ok(jobRequiredSkill);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteJobRequiredSkill (@PathVariable UUID skillId){

        jobSkillService.deleteJobRequiredSkill(skillId);

        return ResponseEntity.ok("Job Required Skill successfully deleted!");
    }

}
