package com.nhatdev.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;
import com.nhatdev.springai.dto.ChatResponse;
import com.nhatdev.springai.service.ChatService;


/**
 * REST API
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(
            @RequestBody ChatRequest request) {
        try {
            logger.info("Received chat request from client");

            ChatResponse response = chatService.chat(request);

            if ("error".equals(response.getStatus())) {
                logger.warn("Chat service returned error: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Unexpected error in chat endpoint: {}", e.getMessage(), e);
            ChatResponse errorResponse = ChatResponse.error(
                    "Error occurs",
                    request.getConversationId()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}