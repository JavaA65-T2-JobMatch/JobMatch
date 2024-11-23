package com.example.demo.service.interfaces;

import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.models.UserEntity;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);
    UserEntity findUserByUsername(String username);
    void deleteUser(int userId, int authenticatedUserId);
    UserEntity updateUser(int userId,UserEntity updatedUserEntity, int authenticatedUserId);

    UserEntity register(UserRegistrationDTO registrationDTO);
    UserEntity login(UserLoginDTO loginDTO);


}
