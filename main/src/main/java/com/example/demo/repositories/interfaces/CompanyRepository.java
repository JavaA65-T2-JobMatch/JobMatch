package com.example.demo.repositories.interfaces;

import com.example.demo.models.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findByCompanyName(String companyName);

    @Query("SELECT c FROM Company c JOIN UserEntity u ON c.user = u.userId WHERE u.username = :username")
    Optional<Company> findByCompanyByUsername(@Param("username")String username);

    Company findCompanyByUser(int userId);
    Company findByCompanyId(int companyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Company p WHERE p.user = :userId")
    void deleteByUserId(@Param("userId") int userId);

    @Query("SELECT c FROM Company c JOIN UserEntity u ON c.user = u.userId WHERE u.username = :username")
    Optional<Company> findByUserUsername(@Param("username")String username);

}
