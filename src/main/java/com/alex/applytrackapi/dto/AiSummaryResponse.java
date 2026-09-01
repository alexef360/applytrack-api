package com.alex.applytrackapi.dto;

public class AiSummaryResponse {

    private Long applications;
    private String summary;

    public AiSummaryResponse(Long applications, String summary) {
        this.applications = applications;
        this.summary = summary;
    }

    public Long getApplications() {
        return applications;
    }

    public String getSummary() {
        return summary;
    }
}
