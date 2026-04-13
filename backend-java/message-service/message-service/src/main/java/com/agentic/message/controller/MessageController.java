package com.agentic.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.agentic.message.dto.MessageRequest;
import com.agentic.message.dto.MessageResponse;
import com.agentic.message.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public MessageResponse saveMessage(@RequestBody MessageRequest request) {
        return messageService.saveMessage(request);
    }

    @GetMapping("/conversation/{conversationId}")
    public Page<MessageResponse> getMessagesByConversationId(
            @PathVariable Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        return messageService.getMessagesByConversationId(
                conversationId,
                PageRequest.of(page, size)
        );
    }
}