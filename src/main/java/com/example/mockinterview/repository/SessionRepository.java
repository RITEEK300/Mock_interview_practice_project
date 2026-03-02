package com.example.mockinterview.repository;

import com.example.mockinterview.entity.Session;
import com.example.mockinterview.entity.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    // Optional helpers
    List<Session> findByTopic(String topic);
    List<Session> findByStatus(SessionStatus status);
}