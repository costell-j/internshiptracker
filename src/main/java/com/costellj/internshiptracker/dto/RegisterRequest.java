package com.costellj.internshiptracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    public String name;
    public String email;
    public String password;
}