package com.costellj.internshiptracker.dto;

import com.costellj.internshiptracker.model.User;

import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String tier;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.tier = user.getTier().name();
    }
}