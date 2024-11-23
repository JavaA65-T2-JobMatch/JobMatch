package com.example.demo.service.interfaces;

import com.example.demo.models.Professional;
import com.example.demo.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ProfessionalService {

    Professional createProfessional(Professional professional);
    Professional updateProfessional(int professionalId, Professional upradetProfessional, UserEntity authenticatedUser);
    void deleteProfessional(int professionalId, UserEntity authenticatedUser);
    Optional<Professional> getProfessionalById(int professionalId);
    List<Professional> getAllProfessionals();
}
