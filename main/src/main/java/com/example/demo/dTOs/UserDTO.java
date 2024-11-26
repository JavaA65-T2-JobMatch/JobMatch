package com.example.demo.dTOs;

public class UserDTO {

    private int userDtoID;
    private String username;
    private String role;

    public int getUserDtoID() {
        return userDtoID;
    }

    public void setUserDtoID(int userDtoID) {
        this.userDtoID = userDtoID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
