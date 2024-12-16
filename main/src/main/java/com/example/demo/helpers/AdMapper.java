package com.example.demo.helpers;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.dTOs.SkillDTO;
import com.example.demo.enums.JobAdStatus;
import com.example.demo.models.Ad;
import com.example.demo.models.Skill;
import com.example.demo.service.interfaces.CityService;
import com.example.demo.service.interfaces.CompanyService;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdMapper {
        private final CompanyService companyService;
        private final CityService cityService;
        private final SkillService skillService;
        private final SkillMapper skillMapper;

    public AdMapper(CompanyService companyService, CityService cityService, SkillService skillService, SkillMapper skillMapper) {
        this.companyService = companyService;
        this.cityService = cityService;
        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    public Ad fromDto(JobAdDTO jobAdDTO){
        Ad ad = new Ad();
        assignFromDto(jobAdDTO, ad);
        return ad;
    }

    public void assignFromDto(JobAdDTO jobAdDTO, Ad ad){
        ad.setTitle(jobAdDTO.getTitle());
        ad.setSalaryMin(jobAdDTO.getSalaryMin());
        ad.setSalaryMax(jobAdDTO.getSalaryMax());
        ad.setDescription(jobAdDTO.getDescription());
        ad.setStatus(JobAdStatus.valueOf(jobAdDTO.getStatus()));
        ad.setCompany(companyService.getCompanyById(jobAdDTO.getCompanyId()));
        ad.setLocation(cityService.findCityById(jobAdDTO.getLocationId()));
        ad.setRequirements(jobAdDTO.getRequirements());
        ad.setSkills(skillsToSet(jobAdDTO.getSkills()));
    }

    private Set<Skill> skillsToSet(String skills){
        String[] skillArray = skills.split(",");

        Set<Skill> convertedSkills = new HashSet<>();
        for(String skill : skillArray){
            Skill extractedSkill = skillService.findSkillById(Integer.parseInt(skill));
            convertedSkills.add(extractedSkill);
        }

        return convertedSkills;
    }
}
