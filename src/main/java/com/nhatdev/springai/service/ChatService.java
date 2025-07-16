package com.nhatdev.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;
import com.nhatdev.springai.dto.ChatResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);


    private final ChatClient chatClient;

    private final Map<String, StringBuilder> conversationHistory = new ConcurrentHashMap<>();

    @Autowired
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Handle chat request
     */
    public ChatResponse chat(ChatRequest request) {
        try {
            logger.info("Processing chat request for conversation: {}", request.getConversationId());
            
            String message = request.getMessage();
            String conversationId = request.getConversationId();
            
            if (message == null || message.trim().isEmpty()) {
                return ChatResponse.error("Message cannot be empty", conversationId);
            }

            // Send request to AI
            String aiResponse = chatClient.prompt()
                    .user(message)
                    .system("You are a helpful AI assistant. Please provide clear and helpful responses.")
                    .call()
                    .content();

            logger.info("AI response received successfully");
            return ChatResponse.success(aiResponse, conversationId);

        } catch (Exception e) {
            logger.error("Error processing chat request: {}", e.getMessage(), e);
            return ChatResponse.error("Error occurs, pls try again: " + e.getMessage(),
                    request.getConversationId());
        }
    }


}