package com.nhatdev.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO res
 */
public class ChatRequest {
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("conversationId")
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
