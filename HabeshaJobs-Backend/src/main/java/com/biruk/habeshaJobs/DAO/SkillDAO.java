package com.biruk.habeshaJobs.DAO;

import com.biruk.habeshaJobs.Model.Common.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillDAO extends JpaRepository<Skill, UUID> {

    Optional<Skill> findBySkillName(String skillName);
    Optional<Skill> findBySkillNameIgnoreCase (String skillName);
}
