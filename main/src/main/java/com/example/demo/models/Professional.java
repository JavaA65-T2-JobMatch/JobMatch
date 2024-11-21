package com.example.demo.models;

import com.example.demo.enums.ProfessionalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "professionals")
public class Professional extends User{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "short_summary")
    private String shortSummary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfessionalStatus status;

    @Column(name = "city", nullable = false)
    private int cityId;

    @Column(name = "profile_picture")
    private String profilePicture;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public void setStatus(ProfessionalStatus status) {
        this.status = status;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
