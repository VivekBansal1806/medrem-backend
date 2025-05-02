package org.example.medrembackend.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoginResponse {
    private Long userId;
    private String username;
    private String email;
    private long phone;
    private LocalDate dob;
    private String profilePictureUrl;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String token;
}
