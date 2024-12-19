package com.example.demo.dTOs;

public class JobApplicationDTO {
    private int professionalId;
    private double desiredSalaryMin;
    private double desiredSalaryMax;
    private int location;
    private String motivation;
    private String skills;



    public JobApplicationDTO() {}

    public JobApplicationDTO(int professionalId, double desiredSalaryMin, double desiredSalaryMax,
                             String motivation,int locationId, String skills) {
        this.professionalId = professionalId;
        this.desiredSalaryMin = desiredSalaryMin;
        this.desiredSalaryMax = desiredSalaryMax;
        this.motivation = motivation;
        this.location = locationId;
        this.skills = skills;
    }

    // Getters and Setters


    public int getLocation() {
        return location;
    }

    public void setLocation(int locationId) {
        this.location = locationId;
    }

    public int getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public double getDesiredSalaryMin() {
        return desiredSalaryMin;
    }

    public void setDesiredSalaryMin(double desiredSalaryMin) {
        this.desiredSalaryMin = desiredSalaryMin;
    }

    public double getDesiredSalaryMax() {
        return desiredSalaryMax;
    }

    public void setDesiredSalaryMax(double desiredSalaryMax) {
        this.desiredSalaryMax = desiredSalaryMax;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

}
