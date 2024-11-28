package com.example.demo.service.interfaces;

import com.example.demo.models.Skill;

import java.util.List;

public interface SkillService {

    List<Skill> getAllSkills();

    Skill findSkillByName(String name);

    Skill saveSkill(Skill skill);

    void deleteSkill(int id);
}
