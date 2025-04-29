package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DTO.OutgoingJobSeekerDTO;
import com.biruk.habeshaJobs.Service.JobSeekerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobSeekers")
@CrossOrigin
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    @Autowired
    public JobSeekerController (JobSeekerService jobSeekerService){
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping
    public ResponseEntity<List<OutgoingJobSeekerDTO>> getAllJobSeekers () {

       List<OutgoingJobSeekerDTO> outgoingJobSeekerDTOs = jobSeekerService.getAllJobSeekers();

       return ResponseEntity.ok(outgoingJobSeekerDTOs);
    }

    @GetMapping("/{jobSeekerId}")
    public ResponseEntity<OutgoingJobSeekerDTO> getJobSeekerById (@PathVariable UUID jobSeekerId) {

        OutgoingJobSeekerDTO outgoingJobSeekerDTO = jobSeekerService.getJobSeekerById(jobSeekerId);

        return ResponseEntity.ok(outgoingJobSeekerDTO);
    }

    @DeleteMapping("/{jobSeekerId}")
    public ResponseEntity<String> deleteJobSeeker (@PathVariable UUID jobSeekerId) {

        jobSeekerService.deleteJobSeeker(jobSeekerId);

        return ResponseEntity.ok("JobSeeker Deleted Successfully!");
    }

    @PatchMapping("/{jobSeekerId}")
    public ResponseEntity<OutgoingJobSeekerDTO> updateJobSeekerProfile (@PathVariable UUID jobSeekerId, @RequestBody JsonNode patchNode) {

        OutgoingJobSeekerDTO outgoingJobSeekerDTO = jobSeekerService.updateJobSeeker(jobSeekerId, patchNode);

        return ResponseEntity.ok(outgoingJobSeekerDTO);

    }
}
