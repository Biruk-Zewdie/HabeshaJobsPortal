package com.biruk.habeshaJobs.Controller;

import com.biruk.habeshaJobs.DTO.IncomingJobDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobDTO;
import com.biruk.habeshaJobs.Model.Job.Job;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.biruk.habeshaJobs.SecurityConfig.JWTUtil;
import com.biruk.habeshaJobs.Service.JobService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@CrossOrigin()
public class JobController {

    private final JobService jobService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JobController (JobService jobService, JWTUtil jwtUtil) {
        this.jobService = jobService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<OutgoingJobDTO> postJob (@RequestBody IncomingJobDTO incomingJobDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){

        String token = authHeader.replace("Bearer ", "");
        UUID userId = jwtUtil.extractUserId(token);
        OutgoingJobDTO outgoingJobDTO = jobService.postJob(incomingJobDTO, userId);

        return ResponseEntity.ok(outgoingJobDTO);

    }

    @GetMapping
    public ResponseEntity<List<OutgoingJobDTO>> getAllJobs () {

        List<OutgoingJobDTO> outgoingJobDTOs = jobService.getAllJobs();

        return ResponseEntity.ok(outgoingJobDTOs);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity <OutgoingJobDTO> getJobByJobId (@PathVariable UUID jobId) {

        OutgoingJobDTO outgoingJobDTO = jobService.getJobById(jobId);

        return ResponseEntity.ok(outgoingJobDTO);

    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob (@PathVariable UUID jobId) {

        jobService.deleteJob(jobId);

        return ResponseEntity.ok("Job deleted successfully!");

    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<OutgoingJobDTO> updateJob (@PathVariable  UUID jobId, @RequestBody JsonNode patchNode) {

        OutgoingJobDTO outgoingJobDTO = jobService.updateJob(jobId, patchNode);

        return ResponseEntity.ok(outgoingJobDTO);
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity <List<OutgoingJobDTO>> getJobsPostedByEmployerId (@PathVariable UUID employerId) {

        List<OutgoingJobDTO> outgoingJobDTOs = jobService.getJobsByEmployerId(employerId);

        return ResponseEntity.ok(outgoingJobDTOs);
    }

    // The below methods are for filtering jobs by different criteria.

    @GetMapping("/search/title")
    public ResponseEntity<List<OutgoingJobDTO>> getJobsByJobTitle(@RequestParam String jobTitle) {

        List<OutgoingJobDTO> outgoingJobDTOs = jobService.searchJobsByJobTitle(jobTitle);

        return ResponseEntity.ok(outgoingJobDTOs);
    }

    @GetMapping("/search/salary")
    public ResponseEntity<List<OutgoingJobDTO>> getJobsBySalary(@RequestParam double minSalary, @RequestParam double maxSalary){

        List <OutgoingJobDTO> outgoingJobDTOs = jobService.searchBySalaryRange(minSalary, maxSalary);

        return ResponseEntity.ok(outgoingJobDTOs);
    }

    @GetMapping("/search/jobType")
    public ResponseEntity<List<OutgoingJobDTO>> getJobsByJobType(@RequestParam Job.JobType jobType){
        List <OutgoingJobDTO> outgoingJobDTOs = jobService.searchByJobType(jobType);
        return ResponseEntity.ok(outgoingJobDTOs);
    }

    @GetMapping("/search/recentJobs")
    public ResponseEntity<List<OutgoingJobDTO>> getJobsPostedWithinNDays (@RequestParam int days){
        List <OutgoingJobDTO> outgoingJobDTOs = jobService.findJobsPostedInLastDays(days);

        return ResponseEntity.ok(outgoingJobDTOs);
    }

    @GetMapping("/search/nearByJobs")
    public ResponseEntity<List<OutgoingJobDTO>> findNearByJobs (@RequestParam UUID jobSeekerId, double radiusInMiles){

        List<OutgoingJobDTO> outgoingJobDTOs = jobService.findNearByJobsForJobSeeker(jobSeekerId, radiusInMiles);

        return ResponseEntity.ok(outgoingJobDTOs);
    }

}
