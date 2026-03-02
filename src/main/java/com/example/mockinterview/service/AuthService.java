package com.example.mockinterview.service;

import com.example.mockinterview.dto.UserDTO;
import com.example.mockinterview.entity.Role;
import com.example.mockinterview.entity.Status;
import com.example.mockinterview.entity.User;
import com.example.mockinterview.repository.UserRepository;
import com.example.mockinterview.util.OTPUtil;
import com.example.mockinterview.security.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       EmailService emailService,
                       JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    // Signup
    public String signup(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.STUDENT);
        user.setStatus(Status.PENDING);
        user.setCollege(userDTO.getCollege());
        user.setBranch(userDTO.getBranch());
        user.setBio(userDTO.getBio());
        user.setResumeLink(userDTO.getResumeLink());
        user.setPreference(userDTO.getPreference());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Generate OTP
        String otp = OTPUtil.generateOtp();

        // Send OTP (Console Print for now)
        emailService.sendOtp(user.getEmail(), otp);

        userRepository.save(user);

        return "Signup successful! OTP sent to email.";
    }

    // Login
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email, user.getRole().name());
    }
}