package com.example.demo.service;

import com.example.demo.enums.MatchStatus;
import com.example.demo.models.Ad;
import com.example.demo.models.Application;
import com.example.demo.models.Match;
import com.example.demo.repositories.interfaces.MatchRepository;
import com.example.demo.service.interfaces.ApplicationService;
import com.example.demo.service.interfaces.JobAdService;
import com.example.demo.service.interfaces.MatchService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final ApplicationService applicationService;
    private final JobAdService jobAdService;
    public MatchServiceImpl(MatchRepository matchRepository, ApplicationService applicationService, JobAdService jobAdService){
        this.matchRepository = matchRepository;
        this.applicationService = applicationService;
        this.jobAdService = jobAdService;
    }

    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
    @Override
    public Optional<Match> getMatchById(int matchId) {
        return matchRepository.findById(matchId);
    }

    @Override
    public void deleteMatch(int id) {
        matchRepository.deleteById(id);
    }

    @Override
    public Match createMatchFromAd(int adId, int appId) {
        Match match = new Match();
        Ad ad = jobAdService.getJobAdById(adId);
        match.setJobAd(ad);
        match.setJobApplication(applicationService.getApplicationById(appId));
        match.setCity(ad.getLocation());
        match.setSkill(new HashSet<>(ad.getSkills()));

        return match;
    }

    @Override
    public Match createMatchFromApplication(int appId, int adId) {
        Match match = new Match();
        Application application = applicationService.getApplicationById(appId);
        match.setJobAd(jobAdService.getJobAdById(adId));
        match.setJobApplication(application);
        match.setCity(application.getLocation());
        match.setSkill(new HashSet<>(application.getSkills()));

        return match;
    }

    @Override
    public List<Match> getAllMatchesByApplicationId(int applicationId) {
        return matchRepository.findAllByJobApplicationId(applicationId);
    }

    // Grab all matches when opening the list of matches on an Ad
    @Override
    public List<Match> getAllMatchesByAdId(int adId) {
        return matchRepository.findAllByJobAdId(adId);
    }

    // can be used on either the Ad screen or the App screen
    @Override
    public List<Match> getAllSuccessfulMatches() {
        return getAllMatches().stream()
                .filter(match -> match.getStatus() == MatchStatus.ACCEPTED).toList();
    }

    // can be used on either the Ad screen or the App screen
    @Override
    public List<Match> getAllRejectedMatches() {
        return getAllMatches().stream()
                .filter(match -> match.getStatus() == MatchStatus.REJECTED).toList();
    }

    @Override
    public Match tryMatching(Match match){
        if (salaryWithinThreshold(match) && skillsMatch(match) && locationMatch(match)){
            match.setStatus(MatchStatus.ACCEPTED);
        } else {
            match.setStatus(MatchStatus.REJECTED);
        }
        return createMatch(match);
    }

    private boolean salaryWithinThreshold(Match match){
        Ad jobAd = match.getJobAd();
        Application application = match.getJobApplication();
        double adMax = jobAd.getSalaryMax();
        double adMin = jobAd.getSalaryMin();
        double appMax = application.getDesiredSalaryMax();
        double appMin = application.getDesiredSalaryMin();
        if (jobAd.getSalaryThreshold() != 0){
            return (appMax <= adMax && appMax >= adMin) ||
                    (appMax >= adMin && appMin <= adMax);
        } else {
            double upperThreshold = adMax + calculatePercentage(jobAd.getSalaryThreshold(), adMax);
            double lowerThreshold = adMin - calculatePercentage(jobAd.getSalaryThreshold(), adMin);
            return (appMax  <= upperThreshold && appMin >= lowerThreshold) ||
                    (appMin >= lowerThreshold && appMin <= upperThreshold);
        }
    }

    private boolean skillsMatch(Match match){
        Ad jobAd = match.getJobAd();
        Application application = match.getJobApplication();
        return jobAd.getSkills().equals(application.getSkills());
    }

    private boolean locationMatch(Match match){
        Ad jobAd = match.getJobAd();
        Application application = match.getJobApplication();
        return jobAd.getLocation().equals(application.getLocation());
    }

    public double calculatePercentage(double obtained, double total){
        return (obtained / 100) * total;
    }
}
