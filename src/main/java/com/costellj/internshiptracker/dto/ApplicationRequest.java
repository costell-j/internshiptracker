package com.costellj.internshiptracker.dto;

import com.costellj.internshiptracker.model.ApplicationStatus;
import com.costellj.internshiptracker.dto.CompanyRequest;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ApplicationRequest {
    private CompanyRequest company;
    private String role;
    private ApplicationStatus status;
    private String location;
    private LocalDate appliedDate;
    private String notes;
}