package com.example.mockinterview.controller;

import com.example.mockinterview.entity.Session;
import com.example.mockinterview.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Create a new session and match random student
    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestParam Long userId, @RequestParam String topic) {
        try {
            Session session = sessionService.createSession(userId, topic);
            return ResponseEntity.ok(session);
        } catch (IllegalArgumentException e) {
            // In case userId or topic is invalid
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // For any unexpected errors
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to create session"));
        }
    }
}