package com.example.demo.models;

import com.example.demo.enums.ApplicationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job_application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    @Column(name = "desired_salary_min", precision = 10)
    private Double desiredSalaryMin;

    @Column(name = "desired_salary_max", precision = 10)
    private Double desiredSalaryMax;

    @Column(name = "motivation", length = 255)
    private String motivation;

    @ManyToOne
    @JoinColumn(name = "location", nullable = false)
    private City location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "skills", // Join table name
            joinColumns = @JoinColumn(name = "id"), // FK to Application
            inverseJoinColumns = @JoinColumn(name = "id") // FK to Skill
    )
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "jobApplication", fetch = FetchType.EAGER)
    private List<Match> matches = new ArrayList<>();

    public Application() {
    }

    // Getters and setters

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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }
}
