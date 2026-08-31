package com.alex.applytrackapi.service;

import com.alex.applytrackapi.dto.ApplicationStatsResponse;
import com.alex.applytrackapi.exceptions.ApplicationNotFoundException;
import com.alex.applytrackapi.model.Application;
import com.alex.applytrackapi.model.ApplicationStatus;
import com.alex.applytrackapi.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public List<Application> findAll() {
        return repository.findAll();
    }

    public List<Application> findByStatus (ApplicationStatus status) {
        return repository.findByStatus(status);
    }

    public Application getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public Application create(Application application) {
        application.setId(null);
        return repository.save(application);
    }

    public Application update(Long id, Application application) {
         return repository.findById(id)
                .map(existing -> {
                    existing.setCompany(application.getCompany());
                    existing.setRole(application.getRole());
                    existing.setStatus(application.getStatus());
                    existing.setAppliedAt(application.getAppliedAt());
                    existing.setJobUrl(application.getJobUrl());
                    existing.setNotes(application.getNotes());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ApplicationNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public ApplicationStatsResponse getStats(){
        List<Application> all = repository.findAll();

        Map<ApplicationStatus, Long> counted = all.stream()
                .collect(Collectors.groupingBy(
                        Application::getStatus,
                        Collectors.counting()
                ));

        Map<ApplicationStatus, Long> byStatus = new EnumMap<>(ApplicationStatus.class);
        for(ApplicationStatus status : ApplicationStatus.values()){
            byStatus.put(status,0L);
        }
        byStatus.putAll(counted);

        long total = all.size();
        return new ApplicationStatsResponse(total, byStatus);
    }
    }

