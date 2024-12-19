package com.example.demo.controllers.MVC;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationMvcController {

    private final ApplicationService applicationService;
    private final JobAdService jobAdService;
    private final JobApplicationMapper jobApplicationMapper;
    private final MatchService matchService;

    public ApplicationMvcController(ApplicationService applicationService, JobAdService jobAdService, JobApplicationMapper jobApplicationMapper, MatchService matchService) {
        this.applicationService = applicationService;
        this.jobAdService = jobAdService;
        this.jobApplicationMapper = jobApplicationMapper;
        this.matchService = matchService;
    }

    @GetMapping
    public String getAllApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "applicationCommands/application-list"; // View for displaying the list of applications
    }

    @GetMapping("/professional/{professionalId}")
    public String getApplicationsByProfessionalId(@PathVariable int professionalId, Model model) {
        List<Application> applications = applicationService.getApplicationsByProfessionalId(professionalId);
        model.addAttribute("applications", applications);
        return "applicationCommands/application-list"; // View for displaying the list of applications for a professional
    }

    @GetMapping("/active")
    public String getActiveApplications(Model model) {
        List<Application> applications = applicationService.getActiveApplications();
        model.addAttribute("applications", applications);
        return "applicationCommands/application-list"; // View for displaying active applications
    }

    @GetMapping("/create")
    public String showCreateApplicationForm(Model model) {
        model.addAttribute("application", new Application());
        return "applicationCommands/create-application"; // View for creating a new application
    }

    @PostMapping
    public String createApplication(@ModelAttribute JobApplicationDTO applicationDTO) {
        applicationService.saveApplication(jobApplicationMapper.fromWebInput(applicationDTO));
        return "redirect:/applications"; // Redirect to the list of applications after creation
    }

    @GetMapping("/edit/{id}")
    public String showEditApplicationForm(@PathVariable int id, Model model) {
        Application application = applicationService.getApplicationById(id);
        if (application == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found with ID: " + id);
        }
        model.addAttribute("application", application);
        return "applicationCommands/edit-application"; // View for editing an application
    }

    @PostMapping("/edit/{id}")
    public String updateApplication(@PathVariable int id, @ModelAttribute Application applicationDetails) {
        Application existingApplication = applicationService.getApplicationById(id);
        if (existingApplication == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found with ID: " + id);
        }

        existingApplication.setStatus(applicationDetails.getStatus());
        existingApplication.setMotivation(applicationDetails.getMotivation());
        existingApplication.setDesiredSalaryMin(applicationDetails.getDesiredSalaryMin());
        existingApplication.setDesiredSalaryMax(applicationDetails.getDesiredSalaryMax());

        applicationService.saveApplication(existingApplication);
        return "redirect:/applications"; // Redirect to the list of applications after update
    }

    @PostMapping("/{appId}/match/{adId}")
    public String matchJobAd(@PathVariable int appId, @PathVariable int adId, Model model) {
        try {

            Match match = matchService.createMatchFromApplication(appId, adId);
            matchService.tryMatching(match);

            model.addAttribute("match", match);
            return "applications/match-success"; // View for successful matching
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "applicationCommands/error-application"; // View for handling errors
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "applicationCommands/error-application";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        return "redirect:/applications"; // Redirect to the list of applications after deletion
    }
}
