package com.example.demo.service.interfaces;

import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.models.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);
    UserEntity findUserByUsername(String username);
    @PreAuthorize("#authenticatedUserId == #userId")
    void deleteUser(int userId, int authenticatedUserId);

    @PreAuthorize("#authenticatedUserId == #userId")
    UserEntity updateUser(int userId, UserEntity updatedUserEntity, String Username);

    void register(UserRegistrationDTO registrationDTO);
    void changePassword(String username,String oldPassword,String newPassword);

    String login(UserLoginDTO loginDTO);

    boolean checkPassword(UserLoginDTO loginDTO);

    UserRole findRoleByUsername(String username);
}
