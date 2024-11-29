package com.example.demo.controllers.REST;

import com.example.demo.dTOs.SkillDTO;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillRestController {

    private final SkillService skillService;

    public SkillRestController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        List<SkillDTO> skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable int id) {
        SkillDTO skill = skillService.getSkillById(id);
        if (skill != null) {
            return ResponseEntity.ok(skill);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO) {
        SkillDTO createdSkill = skillService.createSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable int id, @RequestBody SkillDTO skillDTO) {
        SkillDTO updatedSkill = skillService.updateSkill(id, skillDTO);
        if (updatedSkill != null) {
            return ResponseEntity.ok(updatedSkill);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable int id) {
        boolean deleted = skillService.deleteSkill(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
