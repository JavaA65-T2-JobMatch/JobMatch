package com.example.demo.repositories.interfaces;

import com.example.demo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
    Company findByCompanyId(Long id);
    Company findByCity(Integer city);
}
