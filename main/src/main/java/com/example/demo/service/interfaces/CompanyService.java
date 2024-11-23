package com.example.demo.service.interfaces;

import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company createCompany(Company company);
    Company updateCompany(int companyId, Company updatedCompany, UserEntity authenticationUser);
    void deleteCompany(int companyId, UserEntity authenticationUser);
    Optional<Company> getCompanyById(int CompanyId);
    List<Company> getAllCompanies();

}
