package com.example.demo.repositories.interfaces;

import com.example.demo.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Skill findByName(String name);

}