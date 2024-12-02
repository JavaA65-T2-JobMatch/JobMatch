package com.example.demo.models;

import com.example.demo.enums.MatchStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="matches")
public class Match {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_application_id", nullable = false)
    private Application jobApplication;

    @ManyToOne
    @JoinColumn(name = "job_ad_id", nullable = false)
    private Ad jobAd;

    @Column(name = "salary_threshold_percentage")
    private double thresholdPercent;

    @Column(name = "min_salary", nullable = false)
    private double minSalary;

    @Column(name = "max_salary", nullable = false)
    private double maxSalary;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city_id;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Skill> skill;

    @Column(name = "match_status", nullable = false)
    private MatchStatus status = MatchStatus.PENDING;

    @Column(name = "match_date", nullable = false, updatable = false)
    private LocalDateTime matchDate= LocalDateTime.now();


//    public Match(){}
//
//    public Match(Application jobApplication, Ad jobAd){
//        this.jobApplication = jobApplication;
//        this.jobAd = jobAd;
//    }

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

    public City getCity_id() {
        return city_id;
    }

    public void setCity_id(City city_id) {
        this.city_id = city_id;
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
    }
}
