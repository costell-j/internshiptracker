package com.costellj.internshiptracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.costellj.internshiptracker.dto.InteractionRequest;
import com.costellj.internshiptracker.model.Application;
import com.costellj.internshiptracker.model.Interaction;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.repository.ApplicationRepository;
import com.costellj.internshiptracker.repository.InteractionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InteractionService {
    
    private final InteractionRepository interactionRepository;
    private final ApplicationRepository applicationRepository;

    public Interaction create(Long applicationId, InteractionRequest request, User currentUser) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }

        Interaction interaction = new Interaction();
        interaction.setType(request.getType());
        interaction.setNotes(request.getNotes());
        interaction.setOccurredAt(request.getOccurredAt());
        interaction.setApplication(application);

        return interactionRepository.save(interaction);
    }

    public List<Interaction> getTimeline(Long applicationId, User currentUser) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if(!application.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }
        
        return interactionRepository.findByApplicationIdOrderByOccuredAtDesc(applicationId);
    }

    public void delete(Long interactionId, User currentUser) {
        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new RuntimeException("Interaction not found"));

        if (!interaction.getApplication().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }

        interactionRepository.deleteById(interactionId);
    }
}