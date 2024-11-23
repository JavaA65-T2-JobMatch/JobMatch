package com.example.demo.dTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ProffesionalDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private int cityId;

    private String shortSummary;

    private String profilePicture;
}
