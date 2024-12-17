package com.example.demo.controllers.REST;

import com.example.demo.dTOs.JobApplicationDTO;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.helpers.JobApplicationMapper;
import com.example.demo.models.Ad;
import com.example.demo.models.Application;
import com.example.demo.models.Match;
import com.example.demo.service.interfaces.ApplicationService;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    private final ApplicationService applicationService;
    private final JobAdService jobAdService;
    private final MatchService matchService;
    private final JobApplicationMapper jobApplicationMapper;

    public ApplicationRestController(ApplicationService applicationService, JobAdService jobAdService, MatchService matchService, JobApplicationMapper jobApplicationMapper) {
        this.applicationService = applicationService;
        this.jobAdService = jobAdService;
        this.matchService = matchService;
        this.jobApplicationMapper = jobApplicationMapper;
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
    public ResponseEntity<Application> createApplication(@RequestBody JobApplicationDTO jobApplicationDTO) {
        Application createdApplication = jobApplicationMapper.fromDto(jobApplicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.saveApplication(createdApplication));
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

    @PostMapping("/{appId}/match/{adId}")
    public ResponseEntity<Match> matchJobAd(@PathVariable int appId, @PathVariable int adId){
        try {
            Ad jobAd = jobAdService.getJobAdById(adId);
            Application application = applicationService.getApplicationById(appId);

            Match match = matchService.createMatch(new Match(application, jobAd));
            matchService.tryMatching(match);

            return ResponseEntity.ok(match);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
