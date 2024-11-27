package com.example.demo.models;

import com.example.demo.enums.ApplicationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_application")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    @Column(name = "desired_salary_min", precision = 10, scale = 2)
    private Double desiredSalaryMin;

    @Column(name = "desired_salary_max", precision = 10, scale = 2)
    private Double desiredSalaryMax;

    @Column(name = "motivation", length = 255)
    private String motivation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name = "skillset", nullable = false)
//    private Skill skillset;


    public JobApplication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Double getDesiredSalaryMin() {
        return desiredSalaryMin;
    }

    public void setDesiredSalaryMin(Double desiredSalaryMin) {
        this.desiredSalaryMin = desiredSalaryMin;
    }

    public Double getDesiredSalaryMax() {
        return desiredSalaryMax;
    }

    public void setDesiredSalaryMax(Double desiredSalaryMax) {
        this.desiredSalaryMax = desiredSalaryMax;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
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
