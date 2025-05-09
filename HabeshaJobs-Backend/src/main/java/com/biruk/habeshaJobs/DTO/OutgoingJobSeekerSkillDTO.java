package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;
import com.biruk.habeshaJobs.Service.JobSeekerSkillService;

import java.util.UUID;

public class OutgoingJobSeekerSkillDTO {

    private UUID jssId;
    private String skillName;

    private JobSeekerSkill.SkillLevel skillLevel;

    public OutgoingJobSeekerSkillDTO() {
    }

    public OutgoingJobSeekerSkillDTO(UUID jssId ,String skillName, JobSeekerSkill.SkillLevel skillLevel) {
        this.jssId = jssId;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public OutgoingJobSeekerSkillDTO (JobSeekerSkill jobSeekerSkill){
        this.jssId = jobSeekerSkill.getJobSeekerSkillId();
        this.skillName = jobSeekerSkill.getSkill().getSkillName();
        this.skillLevel = jobSeekerSkill.getSkillLevel();
    }

    public  UUID getJssId () {
        return jssId;
    }

    public void setJssId (UUID jssId){
        this.jssId = jssId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public JobSeekerSkill.SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(JobSeekerSkill.SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return "OutgoingJobSeekerSkillDTO{" +
                "jssId=" + jssId +
                "skillName='" + skillName + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }
}
