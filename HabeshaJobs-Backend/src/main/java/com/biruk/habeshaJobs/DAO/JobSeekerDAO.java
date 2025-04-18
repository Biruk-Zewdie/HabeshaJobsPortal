package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.JobSeeker.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobSeekerDAO extends JpaRepository <JobSeeker, UUID> {

    public Optional<JobSeeker> findByEmail (String email);

}
