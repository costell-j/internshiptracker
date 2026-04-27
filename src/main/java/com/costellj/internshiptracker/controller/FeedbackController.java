package com.costellj.internshiptracker.controller;

import com.costellj.internshiptracker.dto.FeedbackRequest;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Void> submit(
            @RequestBody FeedbackRequest request,
            @AuthenticationPrincipal User currentUser) {
        feedbackService.submit(request, currentUser);
        return ResponseEntity.ok().build();
    }
}