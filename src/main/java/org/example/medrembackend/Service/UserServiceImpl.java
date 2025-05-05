package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.LoginResponse;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Repository.UserRepo;
import org.example.medrembackend.Security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder pe;
    private final JwtHelper jwtHelper;


    @Autowired
    UserServiceImpl(PasswordEncoder pe, UserRepo userRepo, JwtHelper jwtHelper) {
        this.pe = pe;
        this.userRepo = userRepo;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registration) {

        UserEntity user = new UserEntity();
        user.setUsername(registration.getUsername());
        user.setPhone(registration.getPhone());
        user.setEmail(registration.getEmail());
        user.setPassword(pe.encode(registration.getPassword()));
        user.setDob(registration.getDob());
        user.setProfilePictureUrl(registration.getProfilePictureUrl());
        user.setRole(UserEntity.Role.USER);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        UserEntity savedUser = userRepo.save(user);

        return RegistrationResponse.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .dob(savedUser.getDob())
                .profilePictureUrl(savedUser.getProfilePictureUrl())
                .role(savedUser.getRole().name())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {

        UserEntity user = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!pe.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Wrong password");

        String token = jwtHelper.generateToken(org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build());

        return LoginResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dob(user.getDob())
                .profilePictureUrl(user.getProfilePictureUrl())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .token(token)
                .build();
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }

    // New method to get user from JWT token
    public UserEntity getUserFromToken(String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = jwtHelper.getUsernameFromToken(token);
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserEntity getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity updateUser(long id, UserEntity updatedUser) {
        UserEntity existingUser = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getDob() != null) {
            existingUser.setDob(updatedUser.getDob());
        }
        if (updatedUser.getProfilePictureUrl() != null) {
            existingUser.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
        }

        existingUser.setUpdatedAt(LocalDate.now());

        return userRepo.save(existingUser);
    }

}
