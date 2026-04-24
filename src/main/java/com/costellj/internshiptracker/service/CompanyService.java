package com.costellj.internshiptracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.costellj.internshiptracker.dto.CompanyRequest;
import com.costellj.internshiptracker.model.Company;
import com.costellj.internshiptracker.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company findOrCreate(CompanyRequest request) {
        return companyRepository.findByNameIgnoreCase(request.getName())
                .orElseGet(() -> {
                    Company company = new Company();
                    company.setName(request.getName());
                    company.setWebsite(request.getWebsite());
                    company.setIndustry(request.getIndustry());
                    company.setLocation(request.getLocation());
                    return companyRepository.save(company);
                });
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
}