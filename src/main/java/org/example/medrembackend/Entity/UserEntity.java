package org.example.medrembackend.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true, length = 10)
    private long phone;
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDate dob;
    private String profilePictureUrl;
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public enum Role {
        USER, ADMIN
    }
}
