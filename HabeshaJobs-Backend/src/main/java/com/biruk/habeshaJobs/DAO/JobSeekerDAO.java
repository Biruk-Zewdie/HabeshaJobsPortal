package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobSeekerDAO extends JpaRepository <JobSeeker, UUID> {

}
