package com.agentic.ai.dto;

public class AIResponse {

    private String finalResponse;
    private String status;
    private TasksSummary tasksSummary;

    public String getFinalResponse() {
        return finalResponse;
    }

    public void setFinalResponse(String finalResponse) {
        this.finalResponse = finalResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TasksSummary getTasksSummary() {
        return tasksSummary;
    }

    public void setTasksSummary(TasksSummary tasksSummary) {
        this.tasksSummary = tasksSummary;
    }
}