package com.costellj.internshiptracker.dto;

import com.costellj.internshiptracker.model.FeedbackType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {
    private FeedbackType type;
    private String message;
}