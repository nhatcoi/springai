package com.nhatdev.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO res
 */
@Schema(description = "Request object cho chat API")
public class ChatRequest {
    
    @JsonProperty("message")
    @Schema(description = "User message", example = "Hi, How are you?", required = true)
    private String message;
    
    @JsonProperty("conversationId")
    @Schema(description = "Conversation ID to maintain it", example = "conv-123", required = false)
    private String conversationId;
    
    public ChatRequest() {}
    
    public ChatRequest(String message, String conversationId) {
        this.message = message;
        this.conversationId = conversationId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getConversationId() {
        return conversationId;
    }
    
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
    
    @Override
    public String toString() {
        return "ChatRequest{" +
                "message='" + message + '\'' +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }
} 