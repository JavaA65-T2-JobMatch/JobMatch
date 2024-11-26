package com.example.demo.helpers;

import com.example.demo.dTOs.UserDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserDTO dto){
        UserEntity user = new UserEntity();
        user.setUsername(user.getUsername());
        user.setRole(UserRole.valueOf(user.getRole().toString()));
        return user;
    }

    public UserDTO toDto(UserEntity user){
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().toString());
        return dto;
    }
}
