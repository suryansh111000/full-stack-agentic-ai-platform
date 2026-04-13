package com.agentic.ai.service;

import com.agentic.ai.client.RetryablePythonClient;
import com.agentic.ai.dto.AIRequest;
import com.agentic.ai.dto.AIResponse;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final RetryablePythonClient client;

    public AIService(RetryablePythonClient client) {
        this.client = client;
    }

    public AIResponse process(AIRequest request) {
        // For now, just pass request to RetryablePythonClient
        return client.callWithRetry(request);
    }
}