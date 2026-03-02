package com.example.mockinterview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDTO {
    private String message;
    private boolean success;
    private Object data;

    public ResponseDTO(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    // Getters & Setters
}