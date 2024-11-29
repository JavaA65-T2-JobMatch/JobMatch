package com.example.demo.repositories.interfaces;

import com.example.demo.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findAllByJobApplicationId(int id);
    List<Match> findAllByJobAdId(int id);
}
