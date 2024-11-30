package com.example.demo.controllers.REST;

import com.example.demo.enums.ApplicationStatus;
import com.example.demo.models.Application;
import com.example.demo.service.interfaces.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    private final ApplicationService applicationService;

    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<Application>> getApplicationsByProfessionalId(@PathVariable int professionalId) {
        List<Application> applications = applicationService.getApplicationsByProfessionalId(professionalId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Application>> getActiveApplications() {
        List<Application> applications = applicationService.getActiveApplications();
        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationService.saveApplication(application);
        return ResponseEntity.ok(createdApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @PathVariable int id,
            @RequestBody Application applicationDetails) {
        // Assuming there's a method to find and update an application.
        Application existingApplication = applicationService.getAllApplications()
                .stream()
                .filter(app -> app.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

        existingApplication.setStatus(applicationDetails.getStatus());
        existingApplication.setMotivation(applicationDetails.getMotivation());
        existingApplication.setDesiredSalaryMin(applicationDetails.getDesiredSalaryMin());
        existingApplication.setDesiredSalaryMax(applicationDetails.getDesiredSalaryMax());
//        existingApplication.setLocations(applicationDetails.getLocations());

        Application updatedApplication = applicationService.saveApplication(existingApplication);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
