package com.example.mockinterview.controller;

import com.example.mockinterview.entity.User;
import com.example.mockinterview.repository.UserRepository;
import com.example.mockinterview.security.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public ProfileController(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public Map<String, Object> getMyProfile(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "college", user.getCollege(),
                "branch", user.getBranch(),
                "bio", user.getBio()
        );
    }
}