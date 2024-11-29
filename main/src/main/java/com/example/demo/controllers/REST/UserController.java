package com.example.demo.controllers.REST;

import com.example.demo.dTOs.ChangePasswordDTO;
import com.example.demo.dTOs.UserDTO;
import com.example.demo.dTOs.UserLoginDTO;
import com.example.demo.dTOs.UserRegistrationDTO;
import com.example.demo.helpers.UserMapper;
import com.example.demo.models.UserEntity;
import com.example.demo.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO registrationDTO){
        try {
            userService.register(registrationDTO);
            return ResponseEntity.ok("User registered successfully");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO){
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userService.createUser(userEntity);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/change-username")
    public ResponseEntity<String> updateUser(
            @Valid @RequestBody UserDTO userDTO,
            @AuthenticationPrincipal UserEntity authenticatedUser){

        if (authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        try {
            userService.updateUser(authenticatedUser.getUserId(),authenticatedUser,userDTO.getUsername());
            return ResponseEntity.ok("User updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user");
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId,
                                             @AuthenticationPrincipal UserEntity authenticatedUser){
        userService.deleteUser(userId,authenticatedUser.getUserId());
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserEntity authenticatedUser,
            @RequestBody ChangePasswordDTO passwordDTO){

        if (authenticatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        try {
            String username = authenticatedUser.getUsername();

            userService.changePassword(username,passwordDTO.getOldPassword(),passwordDTO.getNewPassword());

            return ResponseEntity.ok("Password changed successfully");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            String token = userService.generateToken(loginDTO.getUsername());

            return ResponseEntity.ok().body(Map.of("token",token));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
