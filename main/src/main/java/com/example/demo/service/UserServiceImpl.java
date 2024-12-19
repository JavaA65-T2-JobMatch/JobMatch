package com.example.demo.service;

import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.models.City;
import com.example.demo.models.Company;
import com.example.demo.models.Professional;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.*;
import com.example.demo.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ProfessionalRepository professionalRepository, CompanyRepository companyRepository,
                           CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.professionalRepository = professionalRepository;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;

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

        if (user.getRole() == UserRole.PROFESSIONAL) {
            professionalRepository.deleteByUserId(userId);
        }
        else if (user.getRole() == UserRole.COMPANY) {
            companyRepository.deleteByUserId(userId);
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
    public void register(UserRegistrationDTO registrationDTO) {

        UserEntity user = new UserEntity();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(UserRole.valueOf(registrationDTO.getRole()));
        UserEntity savedUser = userRepository.save(user);

        if ("PROFESSIONAL".equalsIgnoreCase(registrationDTO.getRole())) {
            Professional professional = new Professional();
            professional.setFirstName(registrationDTO.getFirstName());
            professional.setLastName(registrationDTO.getLastName());
            professional.setEmail(registrationDTO.getEmail());
            professional.setShortSummary(registrationDTO.getDescription());
            professional.setUserId(savedUser.getUserId());

            if (registrationDTO.getCityId() != null){
                Optional<City> city = cityRepository.findById(registrationDTO.getCityId());
                if (city.isPresent()) {
                    professional.setCityId(city.get().getCityId());
                }else {
                    throw new IllegalArgumentException("City not found");
                }
            }
            professionalRepository.save(professional);
        }else if ("COMPANY".equalsIgnoreCase(registrationDTO.getRole())) {
            Company company = new Company();
            company.setCompanyName(registrationDTO.getCompanyName());
            company.setDescription(registrationDTO.getDescription());
            company.setUser(savedUser.getUserId());
            if (registrationDTO.getCityId() != null){
                Optional<City> city = cityRepository.findById(registrationDTO.getCityId());
                if (city.isPresent()) {
                    company.setCity(city.get());
                }else {
                    throw new IllegalArgumentException("City not found");
                }
            }
            companyRepository.save(company);
        }else {
            throw new IllegalArgumentException("Invalid role");
        }

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
        UserEntity user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return "Login successful";
    }
    @Override
    public boolean checkPassword(UserLoginDTO loginDTO) {
        UserEntity user = findUserByUsername(loginDTO.getUsername());
        return passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
    }

    @Override
    public UserRole findRoleByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getRole();
    }

}
