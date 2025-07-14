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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST API
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
@Tag(name = "Chat API", description = "API endpoints for chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Send messages to chat
     * */
    @Operation(summary = "Send message to AI",
            description = "Send a message to the AI and get a response. " )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chat successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request invalid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class)))
    })
    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(
            @Parameter(description = "Chat request with message and conversation ID (optional)",
                    required = true,
                    schema = @Schema(implementation = ChatRequest.class))
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

    /**
     * Delete conversation
     */
    @Operation(summary = "Delete conversation",
            description = "Clear the history of a conversation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete conversation successful"),
            @ApiResponse(responseCode = "404", description = "Conversation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/conversation/{conversationId}")
    public ResponseEntity<String> clearConversation(
            @Parameter(description = "Conversation ID ", required = true)
            @PathVariable String conversationId) {
        try {
            logger.info("Clearing conversation: {}", conversationId);

            if (chatService.conversationExists(conversationId)) {
                return ResponseEntity.notFound().build();
            }

            chatService.clearConversation(conversationId);
            return ResponseEntity.ok("Deleted conversation successfully");

        } catch (Exception e) {
            logger.error("Error clearing conversation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when clearing conversation");
        }
    }

    /**
     * Get conversation history
     */
    @Operation(summary = "Get conversation history",
            description = "Access the history of a conversation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get conversation history successful"),
            @ApiResponse(responseCode = "404", description = "Conversation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/conversation/{conversationId}/history")
    public ResponseEntity<String> getConversationHistory(
            @Parameter(description = "Conversation ID", required = true)
            @PathVariable String conversationId) {
        try {
            logger.info("Getting conversation history: {}", conversationId);

            if (chatService.conversationExists(conversationId)) {
                return ResponseEntity.notFound().build();
            }

            String history = chatService.getConversationHistory(conversationId);
            return ResponseEntity.ok(history);

        } catch (Exception e) {
            logger.error("Error getting conversation history: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when getting conversation history");
        }
    }

    /**
     * Quick test
     */
    @Operation(summary = "Quick test chat",
            description = "Test the chat endpoint with a sample message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/test")
    public ResponseEntity<ChatResponse> testChat(
            @Parameter(description = "Tin nhắn test", required = true, example = "Hello!")
            @RequestParam String message) {
        try {
            ChatRequest request = new ChatRequest(message, null);
            ChatResponse response = chatService.chat(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error in test endpoint: {}", e.getMessage(), e);
            ChatResponse errorResponse = ChatResponse.error("Test endpoint error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}