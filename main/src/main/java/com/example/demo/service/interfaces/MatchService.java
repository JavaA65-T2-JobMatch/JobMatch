package com.example.demo.service.interfaces;

import com.example.demo.models.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    Match createMatch(Match match);
    List<Match> getAllMatches();
    List<Match> getAllMatchesByApplicationId(int applicationId);
    List<Match> getAllMatchesByAdId(int adId);
    List<Match> getAllSuccessfulMatches();
    List<Match> getAllRejectedMatches();
    Optional<Match> getMatchById(int matchId);
    Match tryMatching(Match match);
    void deleteMatch(int id);

    Match createMatchFromAd(int adId, int appId);
    Match createMatchFromApplication(int appId, int adId);
}
