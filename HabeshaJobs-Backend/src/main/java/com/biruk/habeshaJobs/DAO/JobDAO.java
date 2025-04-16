package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobDAO extends JpaRepository<Job, UUID> {

}
