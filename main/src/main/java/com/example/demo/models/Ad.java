package com.example.demo.models;

import com.example.demo.enums.JobAdStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job_adds")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ad_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "company", referencedColumnName = "company_id", nullable = false)
    private Company company;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "salary_min", precision = 10)
    private Double salaryMin;

    @Column(name = "salary_max", precision = 10)
    private Double salaryMax;

    @Column(name = "salary_threshold", columnDefinition = "integer default 0")
    private Double salaryThreshold;


    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "location", nullable = false)
    private City location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobAdStatus status = JobAdStatus.ACTIVE;

    @Column(name="requirements", nullable = false, length = 100)
    private String requirements;



    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name="ads_skills",
                joinColumns = @JoinColumn(name="ad_id"),
                inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "jobAd", fetch = FetchType.LAZY)
    private List<Match> matches;

    public Ad() {
    }

    // Getters and setters

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

    public Double getSalaryThreshold() {
        return salaryThreshold;
    }

    public void setSalaryThreshold(Double salaryThreshold) {
        this.salaryThreshold = salaryThreshold;
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
