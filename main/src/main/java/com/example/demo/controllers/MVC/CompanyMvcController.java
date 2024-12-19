package com.example.demo.controllers.MVC;

import com.example.demo.models.City;
import com.example.demo.models.Company;
import com.example.demo.service.interfaces.CityService;
import com.example.demo.service.interfaces.CompanyService;
import com.example.demo.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyMvcController {

    private final CompanyService companyService;
    private final UserService userService;
    private final CityService cityService;



    private CompanyMvcController(CityService cityService ,CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping("/profile")
    public String companyProfile(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Company> company = companyService.findCompanyByUsername(username);

        model.addAttribute("companyName", company.get().getCompanyName());
        model.addAttribute("contactInfo", company.get().getContactInfo());
        model.addAttribute("description", company.get().getDescription());

        return "companyCommands/companyProfile";
    }

    @GetMapping("/update/{username}")
    public String updateCompany(@PathVariable String username, Model model) {
        Optional<Company> company = companyService.findCompanyByUsername(username);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            model.addAttribute("cities", cityService.getAllCities());
        } else {
            throw new RuntimeException("Company not found");
        }
        return "companyCommands/updateCompany";
    }
    @PostMapping("/update")
    public String processUpdateCompany(@ModelAttribute Company company,
                                       Principal principal
    ){
        String username = principal.getName();
        companyService.updateCompany(username,company);
        return "redirect:/company/profile";
    }

}
