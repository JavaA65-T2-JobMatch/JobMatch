package com.example.demo.dTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobApplicationDTO {
    private int id;
    private int professionalId;
    private BigDecimal desiredSalaryMin;
    private BigDecimal desiredSalaryMax;
    private String motivation;
    private String status;
    private int skillsetId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public JobApplicationDTO() {}

    public JobApplicationDTO(int id, int professionalId, BigDecimal desiredSalaryMin, BigDecimal desiredSalaryMax,
                             String motivation, String status, int skillsetId,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.professionalId = professionalId;
        this.desiredSalaryMin = desiredSalaryMin;
        this.desiredSalaryMax = desiredSalaryMax;
        this.motivation = motivation;
        this.status = status;
        this.skillsetId = skillsetId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public BigDecimal getDesiredSalaryMin() {
        return desiredSalaryMin;
    }

    public void setDesiredSalaryMin(BigDecimal desiredSalaryMin) {
        this.desiredSalaryMin = desiredSalaryMin;
    }

    public BigDecimal getDesiredSalaryMax() {
        return desiredSalaryMax;
    }

    public void setDesiredSalaryMax(BigDecimal desiredSalaryMax) {
        this.desiredSalaryMax = desiredSalaryMax;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSkillsetId() {
        return skillsetId;
    }

    public void setSkillsetId(int skillsetId) {
        this.skillsetId = skillsetId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
