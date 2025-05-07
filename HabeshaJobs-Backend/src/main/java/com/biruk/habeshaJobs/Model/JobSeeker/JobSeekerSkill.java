package com.biruk.habeshaJobs.Model.JobSeeker;

import com.biruk.habeshaJobs.Model.Common.Skill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "JobSeekerSkill")
public class JobSeekerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobSeekerSkillId;

    @ManyToOne
    @JoinColumn(name = "skillId")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "jobSeekerId")
    @JsonIgnore
    private JobSeeker jobSeeker;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    //JobSeeker skills enum
    public enum SkillLevel{
        Beginner,
        Intermediate,
        Expert
    }


    public JobSeekerSkill () {
    }

    public JobSeekerSkill(UUID jobSeekerSkillId, Skill skill, JobSeeker jobSeeker, SkillLevel skillLevel) {
        this.jobSeekerSkillId = jobSeekerSkillId;
        this.skill = skill;
        this.jobSeeker = jobSeeker;
        this.skillLevel = skillLevel;
    }

    public UUID getJobSeekerSkillId() {
        return jobSeekerSkillId;
    }

    public void setJobSeekerSkillId(UUID jobSeekerSkillId) {
        this.jobSeekerSkillId = jobSeekerSkillId;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return "JobSeekerSkill{" +
                "jobSeekerSkillId=" + jobSeekerSkillId +
                ", skill=" + skill +
                ", jobSeeker=" + jobSeeker +
                ", skillLevel=" + skillLevel +
                '}';
    }
}
