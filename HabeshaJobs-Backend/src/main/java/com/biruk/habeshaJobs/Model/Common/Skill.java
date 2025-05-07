package com.biruk.habeshaJobs.Model.Common;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID skillId;

    @Column(unique = true)
    private String skillName;

    public Skill() {
    }

    public Skill(UUID skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public UUID getSkillId() {
        return skillId;
    }

    public void setSkillId(UUID skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
