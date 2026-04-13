package com.agentic.conversation.dto;

import com.agentic.conversation.entity.ConversationStatus;
import java.time.LocalDateTime;

public class ConversationResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private ConversationStatus status;

    public ConversationResponse(Long id,
                                String title,
                                LocalDateTime createdAt,
                                ConversationStatus status) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ConversationStatus getStatus() {
        return status;
    }
}