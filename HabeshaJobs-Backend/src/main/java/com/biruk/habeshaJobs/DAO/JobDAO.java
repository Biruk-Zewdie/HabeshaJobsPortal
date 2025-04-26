package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobDAO extends JpaRepository<Job, UUID> {

    public Optional<List<Job>> findByEmployer_EmployerId(UUID employerId);

}
