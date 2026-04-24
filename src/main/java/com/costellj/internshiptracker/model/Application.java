package com.costellj.internshiptracker.model;

import java.time.LocalDate;

import com.costellj.internshiptracker.model.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applications")
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    private String role;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String location;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}