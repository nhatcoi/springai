package com.nhatdev.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * DTO req
 */
public class ChatResponse {
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("conversationId")
    private String conversationId;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("error")
    private String error;
    
    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = "success";
    }
    
    public ChatResponse(String message, String conversationId) {
        this();
        this.message = message;
        this.conversationId = conversationId;
    }
    
    public static ChatResponse success(String message, String conversationId) {
        return new ChatResponse(message, conversationId);
    }
    
    public static ChatResponse error(String error, String conversationId) {
        ChatResponse response = new ChatResponse();
        response.setError(error);
        response.setStatus("error");
        response.setConversationId(conversationId);
        return response;
    }
    
    // Getters and Setters
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    @Override
    public String toString() {
        return "ChatResponse{" +
                "message='" + message + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
} 