package com.example.demo.service;

import com.example.demo.models.Professional;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.ProfessionalRepository;
import com.example.demo.service.interfaces.ProfessionalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    public Professional updateProfessional(int professionalId, Professional upradetProfessional, UserEntity authenticatedUser) {
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        if (!Objects.equals(professional.getUserId(), authenticatedUser.getUserId())) {
            throw new SecurityException("You do not have permission to update this professional");
        }
        professional.setFirstName(upradetProfessional.getFirstName());
        professional.setLastName(upradetProfessional.getLastName());
        professional.setEmail(upradetProfessional.getEmail());
        professional.setCityId(upradetProfessional.getCityId());
        professional.setShortSummary(upradetProfessional.getShortSummary());
        professional.setProfilePicture(upradetProfessional.getProfilePicture());

        return professionalRepository.save(professional);
    }

    @Override
    public void deleteProfessional(int professionalId, UserEntity authenticatedUser) {
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        if (!Objects.equals(professional.getUserId(), authenticatedUser.getUserId())) {
            throw new SecurityException("You do not have permission to delete this professional");
        }
        professionalRepository.delete(professional);
    }

    @Override
    public Optional<Professional> getProfessionalById(int professionalId) {
        return professionalRepository.findById(professionalId);
    }

    @Override
    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }
}
