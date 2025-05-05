package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.LoginResponse;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;

import java.util.List;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registration);

    LoginResponse loginUser(LoginRequest request);

    List<UserEntity> getUsers();

    UserEntity getUserFromToken(String token);


    UserEntity getUserById(long id);

    UserEntity updateUser(long id, UserEntity updatedUser);
}
