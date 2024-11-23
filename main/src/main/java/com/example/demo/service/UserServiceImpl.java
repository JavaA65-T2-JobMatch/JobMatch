package com.example.demo.service;

import com.example.demo.service.interfaces.UserService;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username ));
    }

    @Override
    public void deleteUser(int userId, int authenticatedUserId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getUserId() != authenticatedUserId) {
            throw new SecurityException("You are not authorized to delete this user account");
        }
        userRepository.delete(user);
    }

    @Override
    public UserEntity updateUser(int userId, UserEntity updatedUserEntity, int authenticatedUserId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (userEntity.getUserId() != authenticatedUserId) {
            throw new SecurityException("You are not authorized to update this user account");
        }

        userEntity.setUsername(updatedUserEntity.getUsername());
        userEntity.setPassword(updatedUserEntity.getPassword());

        return userRepository.save(userEntity);
    }
}
