package com.biruk.habeshaJobs.Model.Job;

import com.biruk.habeshaJobs.Model.Common.Skill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "JobRequiredSkill")
public class JobRequiredSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID skillsRequiredId;

    @ManyToOne
    @JoinColumn(name = "skillId")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "job_Id")
    @JsonIgnore
    private Job job;

    public JobRequiredSkill() {
    }

    public JobRequiredSkill(UUID skillsRequiredId, Skill skill, Job job) {
        this.skillsRequiredId = skillsRequiredId;
        this.skill = skill;
        this.job = job;
    }

    public UUID getSkillsRequiredId() {
        return skillsRequiredId;
    }

    public void setSkillsRequiredId(UUID skillsRequiredId) {
        this.skillsRequiredId = skillsRequiredId;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "JobRequiredSkill{" +
                "skillsRequiredId=" + skillsRequiredId +
                ", skill=" + skill +
                ", job=" + job +
                '}';
    }
}
