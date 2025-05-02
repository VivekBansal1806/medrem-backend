package org.example.medrembackend.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
