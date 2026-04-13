package com.agentic.ai.dto;

import java.util.List;

public class AIRequest {

    private String goal;
    private List<MessageDto> conversationHistory;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public List<MessageDto> getConversationHistory() {
        return conversationHistory;
    }

    public void setConversationHistory(List<MessageDto> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }
}