package com.costellj.internshiptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.costellj.internshiptracker.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}