package com.example.mockinterview.controller;

import com.example.mockinterview.dto.UserDTO;
import com.example.mockinterview.entity.User;
import com.example.mockinterview.repository.UserRepository;
import com.example.mockinterview.security.JWTUtil;
import com.example.mockinterview.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, UserRepository userRepository, JWTUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signup(@RequestBody UserDTO userDTO) {
        String msg = authService.signup(userDTO);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String,String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(email, user.getRole().name());
        return ResponseEntity.ok(Map.of("token", token));
    }
}