package com.example.demo.repositories.interfaces;

import com.example.demo.models.Professional;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {
    Optional<Professional>findByFirstName(String firstName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Professional p WHERE p.user = :user")
    void deleteByUserId(@Param("user") int userId);
}
