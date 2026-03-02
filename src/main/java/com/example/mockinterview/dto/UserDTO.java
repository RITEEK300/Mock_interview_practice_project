package com.example.mockinterview.dto;

import com.example.mockinterview.entity.Preference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String college;
    private String branch;
    private String bio;
    private String resumeLink;
    private Preference preference;
}