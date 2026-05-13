package com.costellj.internshiptracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.costellj.internshiptracker.model.Interaction;


public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByApplicationIdOrderByOccurredAtDesc(Long applicationId);

    @Transactional
    void deleteByApplicationId(Long applicationId);

}