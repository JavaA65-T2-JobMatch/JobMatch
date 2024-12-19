package com.example.demo.service.interfaces;

import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company createCompany(Company company);

    Company updateCompany(String username, Company updatedCompany);

    void deleteCompany(int companyId, UserEntity authenticationUser);
    Company getCompanyById(int CompanyId);
    List<Company> getAllCompanies();
    Company getCompanyByUsername(String username);
    Company findByUserId(int userId);

    Optional<Company> findCompanyByUsername(String username);


}
