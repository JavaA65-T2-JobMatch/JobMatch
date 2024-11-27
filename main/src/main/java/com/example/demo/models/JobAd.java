package com.example.demo.models;

import com.example.demo.enums.JobAdStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_adds")
public class JobAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "salary_min", precision = 10, scale = 2)
    private Double salaryMin;

    @Column(name = "salary_max", precision = 10, scale = 2)
    private Double salaryMax;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "location", nullable = false)
    private City location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobAdStatus status = JobAdStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name = "skillset", nullable = false)
//    private Skill skillset;


    public JobAd() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public JobAdStatus getStatus() {
        return status;
    }

    public void setStatus(JobAdStatus status) {
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
