package com.example.demo.repositories.interfaces;

import com.example.demo.enums.JobAdStatus;
import com.example.demo.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findByCompany_CompanyId(int companyId);

    List<Ad>  findByLocation_CityId(int locationId);

    List<Ad> findByStatus(JobAdStatus status);

    List<Ad> findByTitleContainingIgnoreCase(String keyword);
}
