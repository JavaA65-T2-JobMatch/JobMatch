package com.example.demo.helpers;

import com.example.demo.dTOs.JobApplicationDTO;
import com.example.demo.enums.ApplicationStatus;
import com.example.demo.models.Application;
import com.example.demo.models.Skill;
import com.example.demo.service.interfaces.CityService;
import com.example.demo.service.interfaces.ProfessionalService;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {
    private final ProfessionalService professionalService;
    private final CityService cityService;
    private final SkillService skillService;

    public JobApplicationMapper(ProfessionalService professionalService, CityService cityService, SkillService skillService) {
        this.professionalService = professionalService;
        this.cityService = cityService;
        this.skillService = skillService;
    }
    
    public Application fromDto(JobApplicationDTO jobApplicationDTO){
        Application application = new Application();
        assignFromDto(jobApplicationDTO, application);
        return application;
    }

    private void assignFromDto(JobApplicationDTO jobApplicationDTO, Application application) {
        application.setProfessional(professionalService.findProfessionalById(jobApplicationDTO.getProfessionalId()));
        application.setDesiredSalaryMin(jobApplicationDTO.getDesiredSalaryMin());
        application.setDesiredSalaryMax(jobApplicationDTO.getDesiredSalaryMax());
        application.setMotivation(jobApplicationDTO.getMotivation());
        application.setStatus(ApplicationStatus.ACTIVE);
        application.setLocation(cityService.findCityById(jobApplicationDTO.getLocation()));
        assignSkills(jobApplicationDTO.getSkills(), application);

    }


    private void assignSkills(String skills, Application application){
        String[] skillArray = skills.split(",");
        for(String skill : skillArray){
            Skill extractedSkill = skillService.findSkillById(Integer.parseInt(skill));
            application.getSkills().add(extractedSkill);
        }
    }
}
