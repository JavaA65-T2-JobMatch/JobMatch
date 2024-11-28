package com.example.demo.service.interfaces;

import com.example.demo.models.Ad;

import java.util.List;

public interface JobAdService {

    List<Ad> getAllJobAds();

    List<Ad> getJobAdsByCompanyId(int companyId);

    List<Ad> getActiveJobAds();

    List<Ad> searchJobAdsByTitle(String keyword);

    Ad saveJobAd(Ad jobAd);

    void deleteJobAd(int id);
}
