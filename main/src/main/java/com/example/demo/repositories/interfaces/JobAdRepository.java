package com.example.demo.repositories.interfaces;

import com.example.demo.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAdRepository extends JpaRepository<Ad, Integer> {

    List<Ad> findByCompanyId(int companyId);

    List<Ad> findByLocation(int locationId);

    List<Ad> findByStatus(String status);

    List<Ad> findByTitleContainingIgnoreCase(String keyword);
}
