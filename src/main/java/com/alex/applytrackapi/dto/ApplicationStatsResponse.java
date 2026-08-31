package com.alex.applytrackapi.dto;

import com.alex.applytrackapi.model.ApplicationStatus;

import java.util.Map;

public class ApplicationStatsResponse {

    private long total;
    private Map<ApplicationStatus, Long> byStatus;

    public ApplicationStatsResponse(long total, Map<ApplicationStatus, Long> byStatus) {
        this.total = total;
        this.byStatus = byStatus;
    }

    public long getTotal() {
        return total;
    }
    public Map<ApplicationStatus, Long> getByStatus() {
        return byStatus;
    }

}
