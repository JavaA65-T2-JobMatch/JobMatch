package com.example.demo.service;

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

    @Override
    public List<Match> getAllMatchesByApplicationId(int applicationId) {
        return matchRepository.findAllByJobApplicationId(applicationId);
    }

    @Override
    public List<Match> getAllMatchesByAdId(int adId) {
        return matchRepository.findAllByJobAdId(adId);
    }




}
