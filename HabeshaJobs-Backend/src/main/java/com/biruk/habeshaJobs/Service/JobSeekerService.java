package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.JobSeekerDAO;
import com.biruk.habeshaJobs.DTO.OutgoingJobDTO;
import com.biruk.habeshaJobs.DTO.OutgoingJobSeekerDTO;
import com.biruk.habeshaJobs.Model.Common.GeoHelper;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class JobSeekerService {

    private final JobSeekerDAO jobSeekerDAO;
    private final ObjectMapper objectMapper;
    private final GeoHelper geoHelper;

    @Autowired
    public JobSeekerService (JobSeekerDAO jobSeekerDAO, ObjectMapper objectMapper, GeoHelper geoHelper){
        this.jobSeekerDAO = jobSeekerDAO;
        this.objectMapper = objectMapper;
        this.geoHelper = geoHelper;
    }

    public List<OutgoingJobSeekerDTO> getAllJobSeekers () {

        List<JobSeeker> returnedJobSeekers = jobSeekerDAO.findAll();

        if (returnedJobSeekers.isEmpty()) {
            throw new NoSuchElementException("No job seekers found");
        }

        List <OutgoingJobSeekerDTO> jobSeekerDTOs = new ArrayList<>();

        for (JobSeeker jobSeeker : returnedJobSeekers) {
            OutgoingJobSeekerDTO outgoingJobSeekerDTO = new OutgoingJobSeekerDTO(jobSeeker);
            jobSeekerDTOs.add(outgoingJobSeekerDTO);
        }

        return jobSeekerDTOs;
    }

    public OutgoingJobSeekerDTO getJobSeekerById (UUID JobSeekerId) {

        JobSeeker jobSeeker = jobSeekerDAO.findById(JobSeekerId).orElseThrow(() ->
                new NoSuchElementException("Job Seeker with ID " + JobSeekerId + " not found"));

        return new OutgoingJobSeekerDTO(jobSeeker);
    }

    public void deleteJobSeeker (UUID jobSeekerId) {

        if (!jobSeekerDAO.existsById(jobSeekerId)){
            throw new NoSuchElementException("Job Seeker with ID " + jobSeekerId + " not found");
        }
        jobSeekerDAO.deleteById(jobSeekerId);

    }

    public OutgoingJobSeekerDTO updateJobSeeker (UUID jobSeekerId, JsonNode patchNode){

        JobSeeker jobSeeker = jobSeekerDAO.findById(jobSeekerId).orElseThrow(() ->
                new NoSuchElementException("Job Seeker with Id " + jobSeekerId + " not found"));

        try {

            objectMapper.readerForUpdating(jobSeeker).readValue(patchNode);

            if (jobSeeker.getAddress() != null) {
                jobSeeker.setLocation(geoHelper.createPointFromAddress(jobSeeker.getAddress()));
            }
            JobSeeker updatedJobSeeker = jobSeekerDAO.save(jobSeeker);

            return new OutgoingJobSeekerDTO(updatedJobSeeker);


        }catch (Exception e){
            throw new RuntimeException("Failed to apply patch: " + e.getMessage());
        }
    }

    public Point getJobSeekerLocation (UUID jobSeekersId){

        JobSeeker jobSeeker = jobSeekerDAO.findById(jobSeekersId).orElseThrow(() ->
                new EntityNotFoundException("Job seeker not found")
        );

        return jobSeeker.getLocation();

    }


}
