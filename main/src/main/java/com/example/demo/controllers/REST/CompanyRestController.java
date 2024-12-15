package com.example.demo.controllers.REST;


import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;
import com.example.demo.service.interfaces.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyRestController {

    private final CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getComapnies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        Optional<Company> company = Optional.ofNullable(companyService.getCompanyById(id));
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable int id,
            @Valid @RequestBody Company updatedCompany,
            @AuthenticationPrincipal UserEntity authenticatedUser){
        try {
            Company company = companyService.updateCompany(id, updatedCompany, authenticatedUser);
            return ResponseEntity.ok(company);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (SecurityException e){
            return ResponseEntity.status(403).build();
        }
    }
}
