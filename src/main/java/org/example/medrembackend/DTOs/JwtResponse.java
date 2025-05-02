package org.example.medrembackend.DTOs;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class JwtResponse {

    private String token;
    private String username;
    private Date expiresAt;
    // etc.
}
