package com.example.mockinterview.repository;

import com.example.mockinterview.entity.SessionParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionParticipantsRepository extends JpaRepository<SessionParticipants, Long> {

    List<SessionParticipants> findBySessionId(Long sessionId);

    // Optional helper for future use
    List<SessionParticipants> findByUserId(Long userId);
}