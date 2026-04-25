package com.costellj.internshiptracker.dto;

import java.time.LocalDateTime;

import com.costellj.internshiptracker.model.InteractionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteractionRequest {
    private InteractionType type;
    private String notes;
    private LocalDateTime occurredAt;
}
