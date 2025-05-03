package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
