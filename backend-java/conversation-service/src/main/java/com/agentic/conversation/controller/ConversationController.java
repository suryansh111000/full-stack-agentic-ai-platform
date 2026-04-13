package com.agentic.conversation.controller;

import com.agentic.conversation.dto.CreateConversationRequest;
import com.agentic.conversation.dto.ConversationResponse;
import com.agentic.conversation.service.ConversationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // 🔹 Create
    @PostMapping
    public ConversationResponse createConversation(
            @Valid @RequestBody CreateConversationRequest request) {
        return conversationService.createConversation(request.getTitle());
    }

    // 🔹 Get All (excluding DELETED)
    @GetMapping
    public List<ConversationResponse> getAllConversations() {
        return conversationService.getAllConversations();
    }

    // 🔹 Get By ID
    @GetMapping("/{id}")
    public ConversationResponse getConversationById(@PathVariable Long id) {
        return conversationService.getConversationById(id);
    }

    // 🔹 Close Conversation
    @PatchMapping("/{id}/close")
    public ConversationResponse closeConversation(@PathVariable Long id) {
        return conversationService.closeConversation(id);
    }

    // 🔹 Soft Delete One
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteConversation(@PathVariable Long id) {
        conversationService.softDeleteConversation(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Soft Delete All
    @DeleteMapping
    public ResponseEntity<Void> softDeleteAllConversations() {
        conversationService.softDeleteAllConversations();
        return ResponseEntity.noContent().build();
    }
}