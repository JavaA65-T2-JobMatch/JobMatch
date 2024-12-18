package com.example.demo.controllers.MVC;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.models.Ad;
import com.example.demo.models.Application;
import com.example.demo.models.Match;
import com.example.demo.service.interfaces.ApplicationService;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.MatchService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/job-ads")
public class JobAdMvcController {

    private final JobAdService jobAdService;
    private final ApplicationService applicationService;
    private final MatchService matchService;

    public JobAdMvcController(JobAdService jobAdService, ApplicationService applicationService, MatchService matchService) {
        this.jobAdService = jobAdService;
        this.applicationService = applicationService;
        this.matchService = matchService;
    }

    @GetMapping
    public String listJobAds(Model model) {
        List<Ad> jobAds = jobAdService.getAllJobAds();
        model.addAttribute("jobAds", jobAds);
        return "adCommands/ad-list"; // Return job ad list page
    }

    // Display job ads by company ID
    @GetMapping("/company/{companyId}")
    public String getJobAdsByCompanyId(@PathVariable int companyId, Model model) {
        List<Ad> jobAds = jobAdService.getJobAdsByCompanyId(companyId);
        model.addAttribute("jobAds", jobAds);
        return "/adCommands/ad-list"; // Renders ad-list.html
    }

    // Display active job ads
    @GetMapping("/active")
    public String getActiveJobAds(Model model) {
        List<Ad> activeJobAds = jobAdService.getActiveJobAds();
        model.addAttribute("jobAds", activeJobAds);
        return "adCommands/ad-list"; // Renders ad-list.html
    }

    // Search job ads by title
    @GetMapping("/search")
    public String searchJobAdsByTitle(@RequestParam String keyword, Model model) {
        List<Ad> jobAds = jobAdService.searchJobAdsByTitle(keyword);
        model.addAttribute("jobAds", jobAds);
        model.addAttribute("keyword", keyword);
        return "adCommands/search-results"; // Renders search-results.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("jobAd", new JobAdDTO());
        return "/adCommands/create-ad";
    }


    @PostMapping
    public String saveJobAd(@ModelAttribute @Valid Ad jobAd, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "adCommands/create-ad";
        }
        jobAdService.saveJobAd(jobAd);
        return "redirect:/job-ads";
    }



    // Match a job ad with an application
    @GetMapping("/{adId}/match/{appId}")
    public String matchJobAd(@PathVariable int adId, @PathVariable int appId, Model model) {
        try {
            Ad jobAd = jobAdService.getJobAdById(adId);
            Application application = applicationService.getApplicationById(appId);
            Match match = matchService.createMatch(new Match(application, jobAd));
            matchService.tryMatching(match);

            model.addAttribute("match", match);
            return "adCommands/match-success"; // Renders match-success.html
        } catch (Exception e) {
            model.addAttribute("error", "Error matching job ad: " + e.getMessage());
            return "adCommands/error-ad"; // Renders error-ad.html
        }
    }

    // Show the form for editing a job ad
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Ad jobAd = jobAdService.getJobAdById(id);
            model.addAttribute("jobAd", jobAd);
            return "adCommands/edit-ad"; // Renders edit-ad.html
        } catch (Exception e) {
            model.addAttribute("error", "Job ad not found: " + e.getMessage());
            return "adCommands/error-ad"; // Renders error-ad.html
        }
    }

    // Update an existing job ad
    @PostMapping("/edit/{id}")
    public String updateJobAd(@PathVariable int id, @ModelAttribute Ad jobAd, Model model) {
        try {
            Ad existingJobAd = jobAdService.getJobAdById(id);
            existingJobAd.setTitle(jobAd.getTitle());
            existingJobAd.setDescription(jobAd.getDescription());
            existingJobAd.setStatus(jobAd.getStatus());
            existingJobAd.setSalaryMin(jobAd.getSalaryMin());
            existingJobAd.setSalaryMax(jobAd.getSalaryMax());
            existingJobAd.setLocation(jobAd.getLocation());

            jobAdService.saveJobAd(existingJobAd);
            return "redirect:/job-ads"; // Redirect to the job ads list
        } catch (Exception e) {
            model.addAttribute("error", "Error updating job ad: " + e.getMessage());
            return "adCommands/edit-ad"; // Return to the form with an error
        }
    }

    // Delete a job ad
    @GetMapping("/delete/{id}")
    public String deleteJobAd(@PathVariable int id, Model model) {
        try {
            jobAdService.deleteJobAd(id);
            return "redirect:/job-ads"; // Redirect to the job ads list
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting job ad: " + e.getMessage());
            return "adCommands/error-ad"; // Renders error-ad.html
        }
    }
}
