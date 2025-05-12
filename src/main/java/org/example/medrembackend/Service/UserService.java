package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.*;
import org.example.medrembackend.Entity.UserEntity;

import java.util.List;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequest registration);

    LoginResponse loginUser(LoginRequest request);

    List<UserEntity> getUsers();

    UserEntity getUserFromToken(String token);

    UserEntity getUserById(long id);

    UserEntity updateUser(long id, UpdateUserReq updatedUser);

    UserEntity deleteUserById(long id);
}
