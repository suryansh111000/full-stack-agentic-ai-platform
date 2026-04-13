package com.agentic.ai.client;

import com.agentic.ai.dto.AIRequest;
import com.agentic.ai.dto.AIResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PythonAIClient {

    private final RestTemplate restTemplate;

    public PythonAIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AIResponse callAgent(AIRequest request) {

        String url = "http://localhost:8000/run-agent"; // adjust if needed

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AIRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<AIResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        AIResponse.class
                );

        return response.getBody();
    }
}