package org.example.medrembackend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String username;
    private long phone;
    private String email;
    private String password;
    private LocalDate dob;
    private String profilePictureUrl;
}
