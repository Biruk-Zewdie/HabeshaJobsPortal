package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRequiredSkillDAO extends JpaRepository<JobRequiredSkill, UUID> {
}
