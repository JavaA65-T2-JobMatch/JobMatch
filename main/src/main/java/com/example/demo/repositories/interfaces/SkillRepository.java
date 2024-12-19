package com.example.demo.repositories.interfaces;

import com.example.demo.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findSkillByName(String name);


    Optional<Skill> findSkillById(int id);
}
