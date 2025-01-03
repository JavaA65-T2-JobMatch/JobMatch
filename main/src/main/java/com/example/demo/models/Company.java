package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private int companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @JoinColumn(name = "user",referencedColumnName = "user_id",nullable = false)
    private int user;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "logo")
    private String logo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city")
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
