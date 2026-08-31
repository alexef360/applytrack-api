package com.alex.applytrackapi.controller;

import com.alex.applytrackapi.dto.ApplicationStatsResponse;
import com.alex.applytrackapi.model.Application;
import com.alex.applytrackapi.model.ApplicationStatus;
import com.alex.applytrackapi.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Application> getAll(@RequestParam(required = false) ApplicationStatus status) {
        if (status != null) {
            return service.findByStatus(status);
        }
        return service.findAll();
    }


    @PostMapping
    public ResponseEntity<Application> create(@Valid @RequestBody Application application) {
        Application savedApplication = service.create(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable Long id, @Valid @RequestBody Application application) {
        return ResponseEntity.ok(service.update(id, application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ApplicationStatsResponse getStats() {
        return service.getStats();
    }
}
