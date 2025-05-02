package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.LoginResponse;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder pe;

    @Autowired
    UserServiceImp(PasswordEncoder pe, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.pe = pe;
        this.userRepo = userRepo;
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
                .build();
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }

}
