package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.LoginResponse;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
        logger.info("UserController initialized");
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest registrationReq) {
        logger.info("Register user request received");
        RegistrationResponse response = userService.registerUser(registrationReq);
        logger.info("User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        logger.info("Get all users request received");
        List<UserEntity> users = userService.getUsers();
        logger.info("Users retrieved successfully");
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest req) {
        logger.info("Login user request received");
        LoginResponse response = userService.loginUser(req);
        logger.info("User logged in successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") long id) {
        logger.info("Get user request received");
        UserEntity user = userService.getUserById(id);
        logger.info("User retrieved successfully");
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") long id, @RequestBody UserEntity updatedUser) {
        logger.info("Update user request received for id: {}", id);
        UserEntity user = userService.updateUser(id, updatedUser);
        logger.info("User updated successfully for id: {}", id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logoutUser(@PathVariable("userId") long userId) {
        logger.info("Logout request received for userId: {}", userId);
        // Implement logout logic here, e.g., invalidate session or token if applicable
        logger.info("User logged out successfully for userId: {}", userId);
        return ResponseEntity.ok("User logged out successfully");
    }
}
