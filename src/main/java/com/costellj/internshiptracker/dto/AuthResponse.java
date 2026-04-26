package com.costellj.internshiptracker.dto;

public class AuthResponse {
    public String token;
    public String tier;

    public AuthResponse(String token, String tier) {
        this.token = token;
        this.tier = tier;
    }

    public String getToken() { return token; }
    public String getTier() { return tier; }
}
