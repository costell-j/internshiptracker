package com.costellj.internshiptracker.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.costellj.internshiptracker.dto.FeedbackRequest;
import com.costellj.internshiptracker.model.Feedback;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.repository.FeedbackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final EmailService emailService;

    public void submit(FeedbackRequest request, User currentUser) {
        Feedback feedback = new Feedback();
        feedback.setType(request.getType());
        feedback.setMessage(request.getMessage());
        feedback.setSubmittedByEmail(currentUser.getEmail());
        feedback.setSubmittedAt(LocalDateTime.now());
        feedbackRepository.save(feedback);

        emailService.sendFeedbackNotification(
            request.getType().name(),
            request.getMessage(),
            currentUser.getEmail()
        );
    }
}