package com.example.demo.controllers.REST;

import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;
import com.example.demo.service.interfaces.CompanyService;
import com.example.demo.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
public class CompanyRestController {

    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public CompanyRestController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        Optional<Company> company = Optional.ofNullable(companyService.getCompanyById(id));
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCompany(
            @Valid @RequestBody Company updatedCompany,
            Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(403).body("User not authenticated");
            }

            String username = principal.getName();
            companyService.updateCompany(username, updatedCompany);
            return ResponseEntity.ok("Company profile updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Company not found");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("Forbidden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
