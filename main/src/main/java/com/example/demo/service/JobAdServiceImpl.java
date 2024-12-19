package com.example.demo.service;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.enums.JobAdStatus;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.helpers.AdMapper;
import com.example.demo.models.Ad;
import com.example.demo.repositories.interfaces.JobAdRepository;
import com.example.demo.service.interfaces.JobAdService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAdServiceImpl implements JobAdService {

    private final JobAdRepository jobAdRepository;

    public JobAdServiceImpl(JobAdRepository jobAdRepository) {
        this.jobAdRepository = jobAdRepository;
    }

    @Override
    public List<Ad> getAllJobAds() {
        return jobAdRepository.findAll();
    }

    @Override
    public List<Ad> getJobAdsByCompanyId(int companyId) {
        return jobAdRepository.findByCompanyCompanyId(companyId);
    }

    @Override
    public List<Ad> getActiveJobAds() {
        return jobAdRepository.findByStatus(JobAdStatus.ACTIVE);
    }

    @Override
    public List<Ad> searchJobAdsByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword for search cannot be null or empty.");
        }
        return jobAdRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Ad> searchJobAdsByLocation(int locationId) {
        return jobAdRepository.findByLocationCityId(locationId);
    }

    @Override
    public Ad getJobAdById(int jobAd) {
        return jobAdRepository.getAdById(jobAd);
    }


    @Override
    public Ad saveJobAd(Ad jobAd) {
        if (jobAd == null) {
            throw new IllegalArgumentException("JobAd object cannot be null.");
        }
        return jobAdRepository.save(jobAd);
    }

    @Override
    public void deleteJobAd(int id) {
        if (!jobAdRepository.existsById(id)) {
            throw new EntityNotFoundException("Job ad with ID " + id + " not found.");
        }
        jobAdRepository.deleteById(id);
    }
}
