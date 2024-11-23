package com.example.demo.service.interfaces;

import com.example.demo.models.UserEntity;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);
    UserEntity findUserByUsername(String username);
    void deleteUser(int userId, int authenticatedUserId);
    UserEntity updateUser(int userId,UserEntity updatedUserEntity, int authenticatedUserId);

}
