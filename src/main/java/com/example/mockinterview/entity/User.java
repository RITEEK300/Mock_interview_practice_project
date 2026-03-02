package com.example.mockinterview.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // STUDENT / ADMIN

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING / ACTIVE / INACTIVE

    private String college;
    private String branch;
    private String bio;
    private String resumeLink;

    @Enumerated(EnumType.STRING)
    private Preference preference; // FRONTEND / BACKEND / HR etc

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}