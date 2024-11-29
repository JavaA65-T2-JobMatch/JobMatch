package com.example.demo.dTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobAdDTO {
    private int id;
    private String title;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String description;
    private String status;
    private int companyId;
    private int locationId;
    private String requirements;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public JobAdDTO() {}

    public JobAdDTO(int id, String title, BigDecimal salaryMin, BigDecimal salaryMax, String description,
                    String status, int companyId, int locationId, String requirements,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.description = description;
        this.status = status;
        this.companyId = companyId;
        this.locationId = locationId;
        this.requirements = requirements;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(BigDecimal salaryMin) {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(BigDecimal salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
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
