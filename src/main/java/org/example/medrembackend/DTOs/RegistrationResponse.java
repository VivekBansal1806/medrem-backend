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
public class RegistrationResponse {

    private long userId;
    private String username;
    private long phone;
    private String email;
    private LocalDate dob;
    private String profilePictureUrl;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
