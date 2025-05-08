package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobSeekerSkillDAO extends JpaRepository<JobSeekerSkill, UUID> {

    List<JobSeekerSkill> findByJobSeeker_JobSeekerId (UUID jobSeekerId);

}
