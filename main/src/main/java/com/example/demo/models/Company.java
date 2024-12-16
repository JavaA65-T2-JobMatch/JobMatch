package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="companyId")
    private int companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "user_id")
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
    private int userId;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "logo")
    private String logo;

    @Column(name = "city", nullable = false)
    private int cityId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int city_id) {
        this.cityId = city_id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUser() {
        return userId;
    }

    public void setUser(int user) {
        this.userId = user;
    }
}
