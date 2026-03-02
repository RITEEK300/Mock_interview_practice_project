package com.example.mockinterview.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOtp(String email, String otp) {

        // Abhi real email nahi bhej rahe
        // Sirf console me print hoga

        System.out.println("================================");
        System.out.println("Sending OTP to: " + email);
        System.out.println("OTP: " + otp);
        System.out.println("================================");
    }
}