package com.costellj.internshiptracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.costellj.internshiptracker.dto.InteractionRequest;
import com.costellj.internshiptracker.model.Interaction;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.service.InteractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/applications/{applicationId}/interactions")
@RequiredArgsConstructor
public class InteractionController {
    
    private final InteractionService interactionService;

    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable Long applicationId,
            @RequestBody InteractionRequest request,
            @AuthenticationPrincipal User currentUser) {
        try {
            interactionService.create(applicationId, request, currentUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Interaction>> getTimeline(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(interactionService.getTimeline(applicationId, currentUser));
    }

        @DeleteMapping("/{interactionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long applicationId,
            @PathVariable Long interactionId,
            @AuthenticationPrincipal User currentUser) {
        interactionService.delete(interactionId, currentUser);
        return ResponseEntity.noContent().build();
            }

}
