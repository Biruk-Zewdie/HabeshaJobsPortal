package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Skill;

public class IncomingJobRequiredSkillDTO {

    private String skillName;

    public IncomingJobRequiredSkillDTO() {
    }

    public IncomingJobRequiredSkillDTO(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "IncomingJobRequiredSkillDTO{" +
                "skillName='" + skillName + '\'' +
                '}';
    }
}
