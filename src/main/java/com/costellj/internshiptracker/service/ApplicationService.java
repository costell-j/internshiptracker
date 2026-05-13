package com.costellj.internshiptracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.costellj.internshiptracker.dto.ApplicationRequest;
import com.costellj.internshiptracker.model.Application;
import com.costellj.internshiptracker.model.ApplicationStatus;
import com.costellj.internshiptracker.model.Company;
import com.costellj.internshiptracker.model.User;
import com.costellj.internshiptracker.model.Tier;
import com.costellj.internshiptracker.repository.ApplicationRepository;
import com.costellj.internshiptracker.repository.UserRepository;
import com.costellj.internshiptracker.repository.InteractionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final CompanyService companyService; 
    private final InteractionRepository interactionRepository;

    public Application create(ApplicationRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    
        if (user.getTier() == Tier.FREE) {
            long count = applicationRepository.countByUserId(user.getId());
            if (count >= 30) {
                throw new RuntimeException("Application limit reached");
            }
        }
    
        Company company = companyService.findOrCreate(request.getCompany());
    
        Application app = new Application();
        app.setCompany(company);
        app.setRole(request.getRole());
        app.setStatus(request.getStatus());
        app.setLocation(request.getLocation());
        app.setAppliedDate(request.getAppliedDate());
        app.setNotes(request.getNotes());
        app.setUser(user);
    
        return applicationRepository.save(app);
    }

    public Page<Application> getAll(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return applicationRepository.findByUserId(user.getId(), pageable);
    }

    public Application getById(Long id, User currentUser) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    
        if (!app.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Forbidden");
        }
    
        return app;
    }
    
    public Application updateStatus(Long id, ApplicationStatus status, String email) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Forbidden");  // we'll map this to 403
        }

        app.setStatus(status);
        return applicationRepository.save(app);
    }

    public void delete(Long id, String email) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    
        if (!app.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Forbidden");
        }
    
        interactionRepository.deleteByApplicationId(id);
    
        applicationRepository.deleteById(id);
    }
    
}