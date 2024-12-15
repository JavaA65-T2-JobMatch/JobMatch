package com.example.demo.controllers.MVC;

import com.example.demo.enums.JobAdStatus;
import com.example.demo.models.Ad;
import com.example.demo.models.Application;
import com.example.demo.models.Match;
import com.example.demo.service.interfaces.ApplicationService;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getAllJobAds(Model model) {
        List<Ad> jobAds = jobAdService.getAllJobAds();
        model.addAttribute("jobAds", jobAds);
        return "job-ads/list"; // Thymeleaf template
    }

    @GetMapping("/company/{companyId}")
    public String getJobAdsByCompanyId(@PathVariable int companyId, Model model) {
        List<Ad> jobAds = jobAdService.getJobAdsByCompanyId(companyId);
        model.addAttribute("jobAds", jobAds);
        return "job-ads/company-list"; // Thymeleaf template
    }

    @GetMapping("/active")
    public String getActiveJobAds(Model model) {
        List<Ad> activeJobAds = jobAdService.getActiveJobAds();
        model.addAttribute("jobAds", activeJobAds);
        return "job-ads/active-list"; // Thymeleaf template
    }

    @GetMapping("/search")
    public String searchJobAdsByTitle(@RequestParam String keyword, Model model) {
        List<Ad> jobAds = jobAdService.searchJobAdsByTitle(keyword);
        model.addAttribute("jobAds", jobAds);
        model.addAttribute("keyword", keyword);
        return "job-ads/search-results"; // Thymeleaf template
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("jobAd", new Ad());
        return "job-ads/create"; // Thymeleaf template
    }

    @PostMapping
    public String saveJobAd(@ModelAttribute Ad jobAd, Model model) {
        try {
            jobAdService.saveJobAd(jobAd);
            return "redirect:/job-ads";
        } catch (Exception e) {
            model.addAttribute("error", "Error saving job ad: " + e.getMessage());
            return "job-ads/create";
        }
    }

    @GetMapping("/{adId}/match/{appId}")
    public String matchJobAd(@PathVariable int adId, @PathVariable int appId, Model model) {
        try {
            Ad jobAd = jobAdService.getJobAdById(adId);
            Application application = applicationService.getApplicationById(appId);
            Match match = matchService.createMatch(new Match(application, jobAd));
            matchService.tryMatching(match);

            model.addAttribute("match", match);
            return "job-ads/match-success"; // Thymeleaf template
        } catch (Exception e) {
            model.addAttribute("error", "Error matching job ad: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Ad jobAd = jobAdService.getJobAdById(id);
            model.addAttribute("jobAd", jobAd);
            return "job-ads/edit"; // Thymeleaf template
        } catch (Exception e) {
            model.addAttribute("error", "Job ad not found: " + e.getMessage());
            return "error";
        }
    }

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
            return "redirect:/job-ads";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating job ad: " + e.getMessage());
            return "job-ads/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteJobAd(@PathVariable int id, Model model) {
        try {
            jobAdService.deleteJobAd(id);
            return "redirect:/job-ads";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting job ad: " + e.getMessage());
            return "error";
        }
    }
}
