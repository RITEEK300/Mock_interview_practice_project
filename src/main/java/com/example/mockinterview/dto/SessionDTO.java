package com.example.mockinterview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDTO {
    private String topic;
    private Long userId; // session creator
}