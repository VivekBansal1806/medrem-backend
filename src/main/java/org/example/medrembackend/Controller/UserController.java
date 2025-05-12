package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.*;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Security.CustomUserDetails;
import org.example.medrembackend.Service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest req) {
        logger.info("Login user request received");
        LoginResponse response = userService.loginUser(req);
        logger.info("User logged in successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        logger.info("Get all users request received");
        List<UserEntity> users = userService.getUsers();
        logger.info("Users retrieved successfully");
        return ResponseEntity.ok(users);
    }


    //for api testing
    @GetMapping("/get/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Long id) {
        logger.info("Get user request received");
        UserEntity user = userService.getUserById(id);
        logger.info("User retrieved successfully");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get")
    public ResponseEntity<UserEntity> getUserById(@AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUserId();
        logger.info("Get user request received");
        UserEntity user = userService.getUserById(userId);
        logger.info("User retrieved successfully");
        return ResponseEntity.ok(user);
    }

    //for api testing
    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserReq updatedUser) {
        logger.info("Update user request received for id: {}", id);
        UserEntity user = userService.updateUser(id, updatedUser);
        logger.info("User updated successfully for id: {}", id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<UserEntity> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateUserReq updatedUser) {
        long id = userDetails.getUserId();
        logger.info("Update user request received for id: {}", id);
        UserEntity user = userService.updateUser(id, updatedUser);
        logger.info("User updated successfully for id: {}", id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("id") Long id) {
        logger.info("Delete user request received for id: {}", id);
        UserEntity user=userService.deleteUserById(id);
        logger.info("User deleted successfully for id: {}", id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        //remaining
        logger.info("User requested logout - stateless token removed on client side");
        return ResponseEntity.ok("Logged out successfully. Please remove token on client side.");
    }

}
