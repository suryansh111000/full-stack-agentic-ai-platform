package com.agentic.conversation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Conversation() {}

    public Conversation(String title) {
        this.title = title;
        this.status = ConversationStatus.ACTIVE;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ConversationStatus.ACTIVE;
        }
    }

    // 🔹 Helper methods (very important for clean service logic)

    public void close() {
        if (this.status == ConversationStatus.DELETED) {
            throw new IllegalStateException("Cannot close a deleted conversation");
        }
        this.status = ConversationStatus.CLOSED;
    }

    public void softDelete() {
        this.status = ConversationStatus.DELETED;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }
}