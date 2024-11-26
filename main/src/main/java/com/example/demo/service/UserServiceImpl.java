package com.example.demo.service;

import com.example.demo.Security.JwtConfig;
import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.service.interfaces.UserService;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final JwtConfig jwtConfig;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
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
    public UserEntity updateUser(int userId, UserEntity updatedUserEntity, String newUsername) {
        if (userId != updatedUserEntity.getUserId()) {
            throw new IllegalArgumentException("Unauthorized operation");
        }

        UserEntity userToUpdate = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

        userToUpdate.setUsername(newUsername);

        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public UserEntity register(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        UserEntity user = new UserEntity();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(hashedPassword);

        try {
            user.setRole(UserRole.valueOf(registrationDTO.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + registrationDTO.getRole() +
                    ". Allowed roles are: ADMIN, COMPANY, PROFESSIONAL.");
        }

        return userRepository.save(user);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword,user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );
            return "User authenticated successfully";
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    @Override
    public String generateToken(String username){
        long expirationTime = 3600000;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

}
