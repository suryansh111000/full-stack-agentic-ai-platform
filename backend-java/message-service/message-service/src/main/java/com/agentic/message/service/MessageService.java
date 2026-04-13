package com.agentic.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agentic.message.dto.MessageRequest;
import com.agentic.message.dto.MessageResponse;
import com.agentic.message.entity.Message;
import com.agentic.message.entity.Role;
import com.agentic.message.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageResponse saveMessage(MessageRequest request) {

        Message message = new Message();

        message.setConversationId(request.getConversationId());
        message.setRole(Role.valueOf(request.getRole()));
        message.setContent(request.getContent());

        Message savedMessage = messageRepository.save(message);

        return mapToResponse(savedMessage);
    }

    public Page<MessageResponse> getMessagesByConversationId(
            Long conversationId,
            Pageable pageable
    ) {

        Page<Message> messages =
                messageRepository.findByConversationIdOrderByCreatedAtDesc(
                        conversationId,
                        pageable
                );

        return messages.map(this::mapToResponse);
    }

    private MessageResponse mapToResponse(Message message) {

        MessageResponse response = new MessageResponse();

        response.setMessageId(message.getMessageId());
        response.setConversationId(message.getConversationId());
        response.setRole(message.getRole().name());
        response.setContent(message.getContent());
        response.setCreatedAt(message.getCreatedAt());

        return response;
    }
}