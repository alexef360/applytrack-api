package com.alex.applytrackapi.service;

import com.alex.applytrackapi.dto.ApplicationStatsResponse;
import com.alex.applytrackapi.exceptions.ApplicationNotFoundException;
import com.alex.applytrackapi.model.ApplicationStatus;
import com.alex.applytrackapi.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    ApplicationRepository repository;

    @InjectMocks
    ApplicationService service;

    @Test
    void getStats_whenNoApplications_thenReturnZero() {
        when(repository.findAll()).thenReturn(List.of());

        ApplicationStatsResponse stats = service.getStats();

        assertEquals(0, stats.getTotal());
        for(ApplicationStatus status : ApplicationStatus.values()){
            assertEquals(0, stats.getByStatus().get(status));
        }

    }

    @Test
    void getById_whenMissing_thenThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ApplicationNotFoundException.class, () -> service.getById(99L) );
    }
}
