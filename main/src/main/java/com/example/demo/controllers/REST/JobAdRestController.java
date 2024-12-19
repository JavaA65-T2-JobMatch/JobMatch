package com.example.demo.controllers.REST;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.helpers.AdMapper;
import com.example.demo.models.Ad;
import com.example.demo.models.Match;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/job-ads")
public class JobAdRestController {

    private final JobAdService jobAdService;
    private final MatchService matchService;
    private final AdMapper adMapper;

    public JobAdRestController(JobAdService jobAdService, MatchService matchService, AdMapper adMapper) {
        this.jobAdService = jobAdService;
        this.matchService = matchService;
        this.adMapper = adMapper;
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
    public ResponseEntity<Ad> saveJobAd(@RequestBody JobAdDTO jobAdDTO) {
        Ad createdJobAd = adMapper.fromDto(jobAdDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobAdService.saveJobAd(createdJobAd));
    }
    @PostMapping("/{adId}/match/{appId}")
    public ResponseEntity<Match> matchJobAd(@PathVariable int adId, @PathVariable int appId){
        try {
            Match match = matchService.createMatchFromAd(adId, appId);

            return ResponseEntity.status(HttpStatus.CREATED).body(matchService.tryMatching(match));
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobAd(@PathVariable int id) {
        jobAdService.deleteJobAd(id);
        return ResponseEntity.noContent().build();
    }
}
