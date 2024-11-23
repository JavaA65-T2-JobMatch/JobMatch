package com.example.demo.repositories.interfaces;

import com.example.demo.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional>findByFirstName(String firstName);
}
