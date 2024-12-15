package com.example.demo.service.interfaces;

import com.example.demo.dTOs.SkillDTO;
import com.example.demo.models.Skill;

import java.util.List;

public interface SkillService {

    SkillDTO getSkillById(int id);
    Skill getSkillByName(String name);

    SkillDTO createSkill(SkillDTO skillDTO);

    SkillDTO updateSkill(int id, SkillDTO skillDTO);

    boolean deleteSkill(int id);

    List<SkillDTO> getAllSkills();
}
