package com.example.demo.controllers.REST;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.Professional;
import com.example.demo.models.UserEntity;
import com.example.demo.service.interfaces.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalRestController {

    private final ProfessionalService professionalService;

    @Autowired
    public ProfessionalRestController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @GetMapping
    public ResponseEntity<List<Professional>> getProfessionals() {
        List<Professional> professionals = professionalService.getAllProfessionals();
        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professional> getProfessional(@PathVariable int id) {
        Optional<Professional> professional = professionalService.getProfessionalById(id);
        return professional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professional> updateProfessional(
            @PathVariable int id,
            @Valid @RequestBody Professional updatedProfessional,
            @AuthenticationPrincipal UserEntity authenticatedUser){
        try {
            Professional professional = professionalService.updateProfessional(id, updatedProfessional, authenticatedUser);
            return ResponseEntity.ok(professional);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (SecurityException e){
            return ResponseEntity.status(403).build();
        }
    }

}
