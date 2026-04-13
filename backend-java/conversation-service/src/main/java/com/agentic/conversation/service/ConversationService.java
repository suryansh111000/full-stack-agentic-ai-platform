package com.agentic.conversation.service;

import com.agentic.conversation.dto.ConversationResponse;
import com.agentic.conversation.entity.Conversation;
import com.agentic.conversation.entity.ConversationStatus;
import com.agentic.conversation.repository.ConversationRepository;
import com.agentic.conversation.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    // 🔹 Create Conversation
    public ConversationResponse createConversation(String title) {
        Conversation conversation = new Conversation(title);
        Conversation saved = conversationRepository.save(conversation);

        return mapToResponse(saved);
    }

    // 🔹 Get All (exclude DELETED)
    public List<ConversationResponse> getAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() != ConversationStatus.DELETED)
                .map(this::mapToResponse)
                .toList();
    }

    // 🔹 Get By ID (reject if DELETED)
    public ConversationResponse getConversationById(Long id) {
        Conversation conversation = getActiveOrClosedConversation(id);
        return mapToResponse(conversation);
    }

    // 🔹 Close Conversation
    public ConversationResponse closeConversation(Long id) {
        Conversation conversation = getActiveOrClosedConversation(id);

        conversation.close();
        Conversation updated = conversationRepository.save(conversation);

        return mapToResponse(updated);
    }

    // 🔹 Soft Delete One
    public void softDeleteConversation(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Conversation not found with id: " + id
                ));

        if (conversation.getStatus() == ConversationStatus.DELETED) {
            throw new ResourceNotFoundException(
                    "Conversation not found with id: " + id
            );
        }

        conversation.softDelete();
        conversationRepository.save(conversation);
    }

    // 🔹 Soft Delete All
    public void softDeleteAllConversations() {
        List<Conversation> conversations = conversationRepository.findAll();

        conversations.forEach(c -> {
            if (c.getStatus() != ConversationStatus.DELETED) {
                c.softDelete();
            }
        });

        conversationRepository.saveAll(conversations);
    }

    // 🔹 Helper method
    private Conversation getActiveOrClosedConversation(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Conversation not found with id: " + id
                ));

        if (conversation.getStatus() == ConversationStatus.DELETED) {
            throw new ResourceNotFoundException(
                    "Conversation not found with id: " + id
            );
        }

        return conversation;
    }

    // 🔹 Mapper
    private ConversationResponse mapToResponse(Conversation conversation) {
        return new ConversationResponse(
                conversation.getId(),
                conversation.getTitle(),
                conversation.getCreatedAt(),
                conversation.getStatus()
        );
    }
}