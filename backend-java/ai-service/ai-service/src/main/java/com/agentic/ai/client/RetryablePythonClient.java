package com.agentic.ai.client;

import com.agentic.ai.dto.AIRequest;
import com.agentic.ai.dto.AIResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class RetryablePythonClient {

    private final PythonAIClient client;

    public RetryablePythonClient(PythonAIClient client) {
        this.client = client;
    }

    public AIResponse callWithRetry(AIRequest request) {

        int maxAttempts = 3; // original + 2 retries

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return client.callAgent(request);

            } catch (ResourceAccessException | HttpServerErrorException ex) {

                // Only retry for infra errors
                if (attempt == maxAttempts) {
                    throw ex;
                }

                // Exponential backoff: 1st retry 500ms, 2nd retry 1000ms
                long backoff = (long) Math.pow(2, attempt - 1) * 500;

                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException ignored) {}

            }
        }

        throw new RuntimeException("Unexpected retry failure");
    }
}