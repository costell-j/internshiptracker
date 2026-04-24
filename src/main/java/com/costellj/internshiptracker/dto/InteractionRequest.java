package com.costellj.internshiptracker.dto;

import com.costellj.internshiptracker.model.InteractionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InteractionRequest {
    private InteractionType type;
    private String notes;
    private LocalDateTime occuredAt;
}
