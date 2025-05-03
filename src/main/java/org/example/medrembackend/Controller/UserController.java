package org.example.medrembackend.Controller;

import java.util.List;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.LoginResponse;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
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

}