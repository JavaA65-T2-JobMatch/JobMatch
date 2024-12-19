package com.example.demo.service;

import com.example.demo.models.City;
import com.example.demo.models.Company;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.CompanyRepository;
import com.example.demo.service.interfaces.CityService;
import com.example.demo.service.interfaces.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CompanyServiceImpl  implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CityService cityService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CityService cityService) {
        this.companyRepository = companyRepository;
        this.cityService = cityService;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(String username, Company updatedCompany) {
        Company company = companyRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Company not found for user: " + username));

        City city = cityService.getCityByCityId(updatedCompany.getCity().getCityId());
        if (updatedCompany.getCity() == null || updatedCompany.getCity().getCityId() != city.getCityId()) {
            throw new IllegalArgumentException("City ID is required for updated a company");
        }
        updatedCompany.setCity(city);

        company.setCompanyName(updatedCompany.getCompanyName());
        company.setDescription(updatedCompany.getDescription());
        company.setContactInfo(updatedCompany.getContactInfo());
        company.setCity(city);
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
    public Company getCompanyById(int CompanyId) {
        return companyRepository.findByCompanyId(CompanyId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyByUsername(String username) {
        return companyRepository.findByCompanyName(username);
    }

    @Override
    public Company findByUserId(int userId) {
        return companyRepository.findCompanyByUser(userId);
    }

    @Override
    public Optional<Company> findCompanyByUsername(String username){
        return companyRepository.findByCompanyByUsername(username);
    }

}
