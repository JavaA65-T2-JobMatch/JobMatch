package com.example.demo.service;

import com.example.demo.Security.JwtConfig;
import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.models.City;
import com.example.demo.models.Company;
import com.example.demo.models.Professional;
import com.example.demo.repositories.interfaces.CityRepository;
import com.example.demo.repositories.interfaces.CompanyRepository;
import com.example.demo.repositories.interfaces.ProfessionalRepository;
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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final JwtConfig jwtConfig;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtConfig jwtConfig, ProfessionalRepository professionalRepository, CompanyRepository companyRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
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
                    company.setCity_id(city.get().getCityId());
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
