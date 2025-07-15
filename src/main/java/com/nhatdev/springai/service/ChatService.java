package com.nhatdev.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;
import com.nhatdev.springai.dto.ChatResponse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.nhatdev.springai.utils.LanguageDetect.detectLanguage;
import static com.nhatdev.springai.utils.Prompt.OLD_CONVERSATION_PROMPT;
import static com.nhatdev.springai.utils.Prompt.SYSTEM_PROMPT;

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
            logger.info("Processing chat request: {}", request);

            // Validate input
            if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                return ChatResponse.error("Message is not blank", request.getConversationId());
            }

            // Conversation ID
            String conversationId = request.getConversationId();
            if (conversationId == null || conversationId.trim().isEmpty()) {
                conversationId = UUID.randomUUID().toString();
            }

            // History
            StringBuilder history = conversationHistory.computeIfAbsent(conversationId, k -> new StringBuilder());
            history.append("User: ").append(request.getMessage()).append("\n");
            // prompt
            String promptText = createPromptWithContext(history.toString(), request.getMessage());

            // send req to AI
            String aiResponse = chatClient.prompt()
                    .user(promptText)
                    .system(SYSTEM_PROMPT)
                    .call()
                    .content();

            // add res to history
            history.append("Assistant: ").append(aiResponse).append("\n");

            logger.info("Generated AI response for conversation: {}", conversationId);
            logger.info("Conversation history:\n {}", history.toString());

            return ChatResponse.success(aiResponse, conversationId);

        } catch (Exception e) {
            logger.error("Error processing chat request: {}", e.getMessage(), e);
            return ChatResponse.error("Error occurs, pls try again: " + e.getMessage(),
                    request.getConversationId());
        }
    }

    /**
     * Prompt with old conversation context
     */
    private String createPromptWithContext(String conversationHistory, String currentMessage) throws IOException {
        if (conversationHistory.trim().isEmpty()) {
            return currentMessage;
        }

        String language = detectLanguage(currentMessage);

        logger.info("Detected language: {}", language);

        return String.format(OLD_CONVERSATION_PROMPT + ". Trả lời bằng ngôn ngữ " + language + ".", conversationHistory, currentMessage);
    }

    /**
     * Clear history
     */
    public void clearConversation(String conversationId) {
        if (conversationId != null) {
            conversationHistory.remove(conversationId);
            logger.info("Cleared conversation history for: {}", conversationId);
        }
    }

    /**
     * Get conversation history
     */
    public String getConversationHistory(String conversationId) {
        if (conversationId == null) {
            return "";
        }

        StringBuilder history = conversationHistory.get(conversationId);
        return history != null ? history.toString() : "";
    }

    /**
     * Check if exists
     */
    public boolean conversationExists(String conversationId) {
        return conversationId == null || !conversationHistory.containsKey(conversationId);
    }
}