package com.alex.applytrackapi.service;

import com.alex.applytrackapi.dto.AiSummaryResponse;
import com.alex.applytrackapi.model.Application;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAiService {

    private final ChatClient chatClient;
    private final ApplicationService applicationService;

    public ApplicationAiService(ChatClient.Builder chatClientBuilder,
                                ApplicationService applicationService) {
        this.chatClient = chatClientBuilder.build();
        this.applicationService = applicationService;
    }

    public AiSummaryResponse summarizeApplication(Long id) {
        Application app = applicationService.getById(id);

        String notes = app.getNotes() == null || app.getNotes().isBlank() ? "No notes provided" : app.getNotes();

        String prompt = """
                You are a career coach. Summarize this job application in 2-3 sentences.
                Then suggest one concrete follow-up action.
                Reply in English. Be concise.
                Company: %s
                Role: %s
                Status: %s
                Applied at: %s
                Notes: %s
                """.formatted(
                        app.getCompany(),
                        app.getRole(),
                        app.getStatus(),
                        app.getAppliedAt(),
                        notes);

        String summary = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new AiSummaryResponse(id, summary);
    }
}
