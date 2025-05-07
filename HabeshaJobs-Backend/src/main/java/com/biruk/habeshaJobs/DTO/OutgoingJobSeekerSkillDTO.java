package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;

public class OutgoingJobSeekerSkillDTO {

    private String skillName;

    private JobSeekerSkill.SkillLevel skillLevel;

    public OutgoingJobSeekerSkillDTO() {
    }

    public OutgoingJobSeekerSkillDTO(String skillName, JobSeekerSkill.SkillLevel skillLevel) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
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
                "skillName='" + skillName + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }
}
