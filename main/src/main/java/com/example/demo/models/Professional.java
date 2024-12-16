package com.example.demo.models;

import com.example.demo.enums.ProfessionalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "professionals")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professional_id")
    private int professionalId;


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
    private ProfessionalStatus status = ProfessionalStatus.ACTIVE;

    @Column(name = "city", nullable = false)
    private int cityId;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private int user;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

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

    public int getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public int getUserId() {
        return user;
    }

    public void setUserId(int user_id) {
        this.user = user_id;
    }
}
