package com.example.demo.service.interfaces;

import com.example.demo.models.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> getAllApplications();

    List<Application> getApplicationsByProfessionalId(int professionalId);

    List<Application> getActiveApplications();

    Application saveApplication(Application application);

    void deleteApplication(int id);
}
