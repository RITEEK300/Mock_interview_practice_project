package com.example.mockinterview.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @GetMapping("/me")
    public String profile(HttpSession session) {

        String user = (String) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return "Logged in as: " + user;
    }
}