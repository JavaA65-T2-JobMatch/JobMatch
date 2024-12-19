package com.example.demo.models;

import com.example.demo.enums.MatchStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="matches")
public class Match {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="match_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_application", nullable = false)
    private Application jobApplication;

    @ManyToOne
    @JoinColumn(name = "job_ad", nullable = false)
    private Ad jobAd;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToMany
    @JoinTable(name = "matches_skills",
                joinColumns = @JoinColumn(name="match_id"),
                inverseJoinColumns = @JoinColumn(name="skill_id"))
    private Set<Skill> skill = new HashSet<>();

    @Column(name = "match_status", nullable = false)
    private MatchStatus status = MatchStatus.PENDING;

    @Column(name = "match_date", nullable = false, updatable = false)
    private LocalDateTime matchDate= LocalDateTime.now();




    public Match(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Application getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(Application jobApplication) {
        this.jobApplication = jobApplication;
    }

    public Ad getJobAd() {
        return jobAd;
    }

    public void setJobAd(Ad jobAd) {
        this.jobAd = jobAd;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Skill> getSkill() {
        return skill;
    }

    public void setSkill(Set<Skill> skill) {
        this.skill = skill;
    }
}
