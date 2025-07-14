package com.nhatdev.springai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * DTO req
 */
@Schema(description = "Response object từ chat API")
public class ChatResponse {
    
    @JsonProperty("message")
    @Schema(description = "Response from AI", example = "Hi! I'm fine, Thanks for asking.")
    private String message;
    
    @JsonProperty("conversationId")
    @Schema(description = "Conservation ID", example = "conv-123")
    private String conversationId;
    
    @JsonProperty("timestamp")
    @Schema(description = "Time to create res", example = "2025-07-10T10:30:00")
    private LocalDateTime timestamp;
    
    @JsonProperty("status")
    @Schema(description = "Response status", example = "success", allowableValues = {"success", "error"})
    private String status;
    
    @JsonProperty("error")
    @Schema(description = "Error message")
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