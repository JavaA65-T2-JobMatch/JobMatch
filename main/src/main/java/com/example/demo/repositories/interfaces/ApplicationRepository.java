package com.example.demo.repositories.interfaces;

import com.example.demo.enums.ApplicationStatus;
import com.example.demo.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findAllByProfessional_ProfessionalId(int professionalId);
    List<Application> findByStatus(ApplicationStatus status);

}
