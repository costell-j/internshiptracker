package com.costellj.internshiptracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.costellj.internshiptracker.model.Application;
import com.costellj.internshiptracker.model.ApplicationStatus;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findByUserId(Long userId, Pageable pageable);
    Page<Application> findByUserIdAndPeriod(Long userId, String period, Pageable pageable);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, ApplicationStatus status);
}