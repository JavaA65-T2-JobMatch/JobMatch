package com.example.demo.controllers.REST;

import com.example.demo.enums.JobAdStatus;
import com.example.demo.models.Ad;
import com.example.demo.service.interfaces.JobAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-ads")
public class JobAdRestController {

    private final JobAdService jobAdService;

    public JobAdRestController(JobAdService jobAdService) {
        this.jobAdService = jobAdService;
    }

    @GetMapping
    public ResponseEntity<List<Ad>> getAllJobAds() {
        List<Ad> jobAds = jobAdService.getAllJobAds();
        return ResponseEntity.ok(jobAds);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Ad>> getJobAdsByCompanyId(@PathVariable int companyId) {
        List<Ad> jobAds = jobAdService.getJobAdsByCompanyId(companyId);
        return ResponseEntity.ok(jobAds);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Ad>> getActiveJobAds() {
        List<Ad> activeJobAds = jobAdService.getActiveJobAds();
        return ResponseEntity.ok(activeJobAds);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> searchJobAdsByTitle(@RequestParam String keyword) {
        List<Ad> jobAds = jobAdService.searchJobAdsByTitle(keyword);
        return ResponseEntity.ok(jobAds);
    }

    @PostMapping
    public ResponseEntity<Ad> saveJobAd(@RequestBody Ad jobAd) {
        Ad createdJobAd = jobAdService.saveJobAd(jobAd);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJobAd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobAd(@PathVariable int id) {
        jobAdService.deleteJobAd(id);
        return ResponseEntity.noContent().build();
    }
}
