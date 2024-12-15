package com.example.demo.dTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobAdDTO {

    private String title;
    private double salaryMin;
    private double salaryMax;
    private String description;
    private String status;
    private int companyId;
    private int locationId;
    private String requirements;


    public JobAdDTO() {}

    public JobAdDTO( String title, double salaryMin, double salaryMax, String description,
                    String status, int companyId, int locationId, String requirements
                    ) {
        this.title = title;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.description = description;
        this.status = status;
        this.companyId = companyId;
        this.locationId = locationId;
        this.requirements = requirements;

    }

    // Getters and Setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(double salaryMax) {
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

  }
