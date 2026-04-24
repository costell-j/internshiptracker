package com.costellj.internshiptracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    private String name;
    private String website;
    private String industry;
    private String location;
}