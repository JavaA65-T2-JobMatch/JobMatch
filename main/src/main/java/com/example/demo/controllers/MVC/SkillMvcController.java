package com.example.demo.controllers.MVC;

import com.example.demo.dTOs.SkillDTO;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skills")
public class SkillMvcController {

    private final SkillService skillService;

    public SkillMvcController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public String getAllSkills(Model model) {
        List<SkillDTO> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "skill-list";
    }

    @GetMapping("/{id}")
    public String getSkillById(@PathVariable int id, Model model) {
        try {
            SkillDTO skill = skillService.getSkillById(id);
            model.addAttribute("skill", skill);
            return "skill-detail";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Skill not found with ID: " + id);
            return "error-skill";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("skill", new SkillDTO());
        return "edit-skill";
    }

    @PostMapping
    public String createSkill(@ModelAttribute SkillDTO skillDTO, Model model) {
        try {
            SkillDTO createdSkill = skillService.createSkill(skillDTO);
            return "redirect:/skills";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error creating skill: " + e.getMessage());
            return "create-skill";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            SkillDTO skill = skillService.getSkillById(id);
            model.addAttribute("skill", skill);
            return "edit-skill";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Skill not found with ID: " + id);
            return "error-skill";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateSkill(@PathVariable int id, @ModelAttribute SkillDTO skillDTO, Model model) {
        try {
            skillService.updateSkill(id, skillDTO);
            return "redirect:/skills";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error updating skill: " + e.getMessage());
            return "edit-skill";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSkill(@PathVariable int id, Model model) {
        try {
            skillService.deleteSkill(id);
            return "redirect:/skills";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error deleting skill: " + e.getMessage());
            return "error-skill";
        }
    }
}
