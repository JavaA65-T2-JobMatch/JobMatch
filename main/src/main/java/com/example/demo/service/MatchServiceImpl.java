package com.example.demo.service;

import com.example.demo.enums.MatchStatus;
import com.example.demo.models.Match;
import com.example.demo.repositories.interfaces.MatchRepository;
import com.example.demo.service.interfaces.MatchService;

import java.util.List;
import java.util.Optional;

public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    public MatchServiceImpl(MatchRepository matchRepository){
        this.matchRepository = matchRepository;
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

    // get all matches when opening the list of matches on an application
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


}
