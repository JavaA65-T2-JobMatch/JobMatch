package com.example.demo.service;

import com.example.demo.models.Skill;
import com.example.demo.repositories.interfaces.SkillRepository;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findSkillByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(int id) {
        skillRepository.deleteById(id);
    }
}
