package com.example.demo.repositories.interfaces;

import com.example.demo.models.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByCompanyName(String companyName);
    Company findByCompanyId(int companyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Company p WHERE p.user = :userId")
    void deleteByUserId(@Param("userId") int userId);
}
