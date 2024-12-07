package com.example.demo.service;

import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.CompanyRepository;
import com.example.demo.service.interfaces.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class CompanyServiceImpl  implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(int companyId, Company updatedCompany, UserEntity authenticationUser) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        if (company.getUser() != authenticationUser.getUserId()) {
            throw new SecurityException("You are not authorized to update this company");
        }
        company.setCompanyName(updatedCompany.getCompanyName());
        company.setDescription(updatedCompany.getDescription());
        company.setContactInfo(updatedCompany.getContactInfo());
        company.setCityId(updatedCompany.getCityId());
        company.setLogo(updatedCompany.getLogo());

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(int companyId, UserEntity authenticationUser) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        if (company.getUser() != authenticationUser.getUserId()) {
            throw new SecurityException("You are not authorized to delete this company");
        }

        companyRepository.delete(company);
    }

    @Override
    public Optional<Company> getCompanyById(int CompanyId) {
        return companyRepository.findById(CompanyId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
