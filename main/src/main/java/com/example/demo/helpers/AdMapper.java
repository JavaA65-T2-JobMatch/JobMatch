package com.example.demo.helpers;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.enums.JobAdStatus;
import com.example.demo.models.Ad;
import com.example.demo.models.Skill;
import com.example.demo.service.interfaces.CityService;
import com.example.demo.service.interfaces.CompanyService;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.SkillService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdMapper {
        private final JobAdService jobAdService;
        private final CompanyService companyService;
        private final CityService cityService;
        private final SkillService skillService;

    public AdMapper(JobAdService jobAdService, CompanyService companyService, CityService cityService, SkillService skillService) {
        this.jobAdService = jobAdService;
        this.companyService = companyService;
        this.cityService = cityService;
        this.skillService = skillService;
    }

    public Ad fromDto(JobAdDTO jobAdDTO){
        Ad ad = new Ad();
        ad.setTitle(jobAdDTO.getTitle());
        ad.setSalaryMin(jobAdDTO.getSalaryMin());
        ad.setSalaryMax(jobAdDTO.getSalaryMax());
        ad.setDescription(jobAdDTO.getDescription());
        ad.setStatus(JobAdStatus.valueOf(jobAdDTO.getStatus()));
        ad.setCompany(companyService.getCompanyById(jobAdDTO.getCompanyId()));
        ad.setLocation(cityService.findCityById(jobAdDTO.getLocationId()));
        ad.setSkills(skillsToSet(jobAdDTO.getRequirements()));
        return ad;
    }

    private Set<Skill> skillsToSet(String skills){
        String[] skillArray = skills.split(",");

        Set<Skill> convertedSkills = new HashSet<>();
        for(String skill : skillArray){
            convertedSkills.add(skillService.getSkillByName(skill));
        }

        return convertedSkills;
    }
}
