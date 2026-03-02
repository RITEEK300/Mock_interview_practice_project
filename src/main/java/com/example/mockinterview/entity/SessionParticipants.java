package com.example.mockinterview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "session_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus status; // INVITED / JOINED / LEFT

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = ParticipantStatus.INVITED; // default
        }
    }
}