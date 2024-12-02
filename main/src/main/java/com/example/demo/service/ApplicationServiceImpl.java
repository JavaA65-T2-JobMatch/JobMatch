package com.example.demo.service;

import com.example.demo.enums.ApplicationStatus;
import com.example.demo.models.Application;
import com.example.demo.repositories.interfaces.ApplicationRepository;
import com.example.demo.service.interfaces.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getApplicationsByProfessionalId(int professionalId) {
        return applicationRepository.findAllByProfessional_ProfessionalId(professionalId);
    }

    @Override
    public List<Application> getActiveApplications() {
        return applicationRepository.findByStatus(ApplicationStatus.ACTIVE);
    }

    //ToDo Add find successful and rejected matches for an app

    @Override
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(int id) {
        applicationRepository.deleteById(id);
    }
}
