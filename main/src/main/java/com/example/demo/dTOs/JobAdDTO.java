package com.example.demo.dTOs;

public class JobAdDTO {

    private String title;
    private double salaryMin;
    private double salaryMax;
    private double salaryThreshold;
    private String description;
    private String requirements;
    private int company;
    private int locationId;
    private String skills;


    public JobAdDTO() {}




    public JobAdDTO(String title, double salaryMin, double salaryMax, double salaryThreshold, String description,
                    String requirements, int company, int locationId, String skills
                    ) {
        this.title = title;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.salaryThreshold = salaryThreshold;
        this.description = description;
        this.requirements = requirements;
        this.company = company;
        this.locationId = locationId;
        this.skills = skills;

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

    public int getCompany() { return company; }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
    public double getSalaryThreshold() {
        return salaryThreshold;
    }

    public void setSalaryThreshold(double salaryThreshold) {
        this.salaryThreshold = salaryThreshold;
    }
    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

  }
