package com.example.demo.service.interfaces;

import com.example.demo.dTOs.JobAdDTO;
import com.example.demo.models.Ad;

import java.util.List;

public interface JobAdService {

    List<Ad> getAllJobAds();

    List<Ad> getJobAdsByCompanyId(int companyId);

    List<Ad> getActiveJobAds();

    List<Ad> searchJobAdsByTitle(String keyword);

    List<Ad> searchJobAdsByLocation(int locationId);

    Ad getJobAdById(int jobAd);

    Ad saveJobAd(Ad jobAd);

    void deleteJobAd(int id);
}
