package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.DAO.SkillDAO;
import com.biruk.habeshaJobs.Model.Common.Skill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

// the main purpose of using the below methods is to reduce duplication of skill-handling logic across different services.
//used as a main utility layer for any domain service that interacts with skills.
@Service
public class SkillService {

    private final SkillDAO skillDAO;

    public SkillService (SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }

    public Skill getSkillById (UUID skillId){

        return skillDAO.findById(skillId).orElseThrow(() ->
                new NoSuchElementException("The skill with Id : " + skillId + " not Found"));
    }

    //Useful for populating dropdowns, auto-complete, or general listing of skills.
    public List<Skill> getAllSkills () {
        return skillDAO.findAll();
    }


    public Skill getOrCreateSkill (String skillName){

        return skillDAO.findBySkillNameIgnoreCase(skillName).orElseGet( () -> {
                    Skill newSkill = new Skill();
                    newSkill.setSkillName(skillName);
                    return newSkill;
                });
    }

    // updateSkill can be used with renaming or editing skills.
    public Skill updateSkill (UUID skillId, String newSkillName) {
        Skill skill = getSkillById(skillId);

        if(skillDAO.findBySkillNameIgnoreCase(newSkillName).isPresent()){
            throw new IllegalArgumentException("Skill with name: " + newSkillName + " already exists");
        }

        skill.setSkillName(newSkillName);
        return skillDAO.save(skill);
    }

    public void deleteSkillById (UUID skillId) {
       if (!skillDAO.existsById(skillId)){
           throw new NoSuchElementException("Skill with ID "+ skillId + " not found");
       }
       skillDAO.deleteById(skillId);
    }
}
