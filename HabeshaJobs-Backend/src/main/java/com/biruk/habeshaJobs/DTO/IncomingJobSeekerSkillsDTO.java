package com.biruk.habeshaJobs.DTO;

import com.biruk.habeshaJobs.Model.Common.Skill;
import com.biruk.habeshaJobs.Model.Job.JobRequiredSkill;
import com.biruk.habeshaJobs.Model.JobSeeker.JobSeekerSkill;

public class IncomingJobSeekerSkillsDTO {

    private String skillName;

    private JobSeekerSkill.SkillLevel skillLevel;

    public IncomingJobSeekerSkillsDTO(){

    }

    public IncomingJobSeekerSkillsDTO (String skillName, JobSeekerSkill.SkillLevel skillLevel){
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public String getSkillName (){
        return skillName;
    }

    public void setSkillName (String skillName){
        this.skillName = skillName;
    }

    public JobSeekerSkill.SkillLevel getSkillLevel (){
        return skillLevel;
    }

    public void setSkillLevel (JobSeekerSkill.SkillLevel skillLevel){
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return "IncomingJobSeekerSkillsDTO{" +
                "skillName='" + skillName + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }
}
