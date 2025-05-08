package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRequiredSkillDAO extends JpaRepository<JobRequiredSkill, UUID> {
    List<JobRequiredSkill> findByJob_JobId (UUID jobId);
}
