package com.example.demo.service;

import com.example.demo.dTOs.SkillDTO;
import com.example.demo.models.Skill;
import com.example.demo.repositories.interfaces.SkillRepository;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillDTO getSkillById(int id) {
        Skill skill = skillRepository.findSkillById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));
        return convertToDTO(skill);
    }

    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {

        Optional<Skill> existingSkill = Optional.ofNullable(skillRepository.findByName(skillDTO.getName()));
        if (existingSkill.isPresent()) {
            throw new RuntimeException("Skill already exists with name: " + skillDTO.getName());
        }
        Skill skill = convertToEntity(skillDTO);
        Skill savedSkill = skillRepository.save(skill);
        return convertToDTO(savedSkill);
    }

    @Override
    public SkillDTO updateSkill(int id, SkillDTO skillDTO) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));

        existingSkill.setName(skillDTO.getName());
        Skill updatedSkill = skillRepository.save(existingSkill);

        return convertToDTO(updatedSkill);
    }

    @Override
    public Skill findSkillById(int id) {
        return skillRepository.findSkillById(id).orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));
    }

    @Override
    public boolean deleteSkill(int id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found with ID: " + id);
        }
        skillRepository.deleteById(id);
        return true;
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SkillDTO convertToDTO(Skill skill) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(skill.getId());
        skillDTO.setName(skill.getName());
        return skillDTO;
    }

    private Skill convertToEntity(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        return skill;
    }
}
