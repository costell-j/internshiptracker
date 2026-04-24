package com.costellj.internshiptracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationStats {
    private long total;
    private long applied;
    private long interviewing;
    private long offer;
    private long rejected;
    private long accepted;
}