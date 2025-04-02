package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobApplicationDAO extends JpaRepository<JobApplication, UUID> {
    
}
