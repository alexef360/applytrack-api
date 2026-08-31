package com.alex.applytrackapi.repository;

import com.alex.applytrackapi.model.Application;
import com.alex.applytrackapi.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStatus(ApplicationStatus status);
}
