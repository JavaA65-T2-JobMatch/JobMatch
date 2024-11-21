package com.example.demo.repositories.interfaces;

import com.example.demo.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Professional findByName(String name);
    Professional findByCity(Integer city);
    Professional findByStatus(String status);
}
