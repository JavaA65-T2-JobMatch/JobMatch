package com.example.demo.service.interfaces;

import com.example.demo.models.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    Match createMatch(Match match);
    List<Match> getAllMatches();
    List<Match> getAllMatchesByApplicationId(int applicationId);
    List<Match> getAllMatchesByAdId(int adId);
    Optional<Match> getMatchById(int matchId);
    void deleteMatch(int id);
}
