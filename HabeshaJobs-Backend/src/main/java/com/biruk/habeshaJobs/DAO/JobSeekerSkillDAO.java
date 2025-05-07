package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobSeekerSkillDAO extends JpaRepository<JobSeekerSkill, UUID> {

}
