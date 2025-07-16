package com.nhatdev.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;
import com.nhatdev.springai.service.ChatService;

import java.util.List;
import java.util.Map;

/**
 * REST API for demonstrating PromptTemplate usage in Spring AI
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

    /**
     * Original chat endpoint (backward compatibility)
     */
    @PostMapping("/test")
    public ResponseEntity<?> testChat(@RequestBody ChatRequest request) {
        try {
            return ResponseEntity.ok(chatService.chat(request));
        } catch (Exception e) {
            logger.error("Error in test endpoint: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Demo 1: Basic PromptTemplate usage
     * 
     * Example request:
     * POST /api/chat/demo/basic
     * {
     *   "message": "What is Spring Boot?",
     *   "assistantName": "TechHelper",
     *   "tone": "enthusiastic and educational"
     * }
     */
    @PostMapping("/demo/basic")
    public ResponseEntity<?> demoBasicTemplate(
            @RequestBody Map<String, Object> requestBody) {
        try {
            ChatRequest request = new ChatRequest((String) requestBody.get("message"), null);
            String assistantName = (String) requestBody.get("assistantName");
            String tone = (String) requestBody.get("tone");

            String response = chatService.chatWithBasicTemplate(request, assistantName, tone);
            return ResponseEntity.ok(Map.of(
                    "response", response,
                    "demo", "Basic PromptTemplate with simple placeholders",
                    "template_used", "Basic template with assistantName, tone, and question placeholders"
            ));
        } catch (Exception e) {
            logger.error("Error in basic template demo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Demo 2: Multi-message PromptTemplate
     * 
     * Example request:
     * POST /api/chat/demo/multi-message
     * {
     *   "message": "How to implement JWT authentication?",
     *   "userRole": "backend developer",
     *   "taskType": "security implementation"
     * }
     */
    @PostMapping("/demo/multi-message")
    public ResponseEntity<?> demoMultiMessageTemplate(
            @RequestBody Map<String, Object> requestBody) {
        try {
            ChatRequest request = new ChatRequest((String) requestBody.get("message"), null);
            String userRole = (String) requestBody.get("userRole");
            String taskType = (String) requestBody.get("taskType");

            String response = chatService.chatWithMultiMessageTemplate(request, userRole, taskType);
            return ResponseEntity.ok(Map.of(
                    "response", response,
                    "demo", "Multi-message PromptTemplate with System and User messages",
                    "template_used", "Separate templates for system and user messages"
            ));
        } catch (Exception e) {
            logger.error("Error in multi-message template demo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Demo 3: Complex PromptTemplate with conditional content
     * 
     * Example request:
     * POST /api/chat/demo/complex
     * {
     *   "message": "Explain microservices architecture",
     *   "contextData": {
     *     "assistantName": "ArchBot",
     *     "assistantRole": "solution architect",
     *     "userLevel": "advanced",
     *     "domain": "software architecture",
     *     "responseStyle": "technical and detailed",
     *     "includeExamples": true,
     *     "includeResources": true
     *   }
     * }
     */
    @PostMapping("/demo/complex")
    public ResponseEntity<?> demoComplexTemplate(
            @RequestBody Map<String, Object> requestBody) {
        try {
            ChatRequest request = new ChatRequest((String) requestBody.get("message"), null);
            @SuppressWarnings("unchecked")
            Map<String, Object> contextData = (Map<String, Object>) requestBody.get("contextData");
            
            if (contextData == null) {
                contextData = Map.of();
            }

            String response = chatService.chatWithComplexTemplate(request, contextData);
            return ResponseEntity.ok(Map.of(
                    "response", response,
                    "demo", "Complex PromptTemplate with conditional content and dynamic building",
                    "template_used", "Dynamic template with conditional instructions based on context",
                    "context_applied", contextData
            ));
        } catch (Exception e) {
            logger.error("Error in complex template demo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Demo 4: Predefined PromptTemplates from utility class
     * 
     * Available template types: system, conversation, greeting, code, problem, knowledge, troubleshooting, tutorial
     * 
     * Example request:
     * POST /api/chat/demo/predefined
     * {
     *   "message": "How to fix NullPointerException in Java?",
     *   "templateType": "troubleshooting",
     *   "parameters": {
     *     "systemType": "Java Application",
     *     "errorCategory": "Runtime Exception",
     *     "operatingSystem": "Linux",
     *     "version": "Java 17",
     *     "configuration": "Spring Boot 3.x"
     *   }
     * }
     */
    @PostMapping("/demo/predefined")
    public ResponseEntity<?> demoPredefinedTemplate(
            @RequestBody Map<String, Object> requestBody) {
        try {
            ChatRequest request = new ChatRequest((String) requestBody.get("message"), null);
            String templateType = (String) requestBody.getOrDefault("templateType", "system");
            @SuppressWarnings("unchecked")
            Map<String, Object> parameters = (Map<String, Object>) requestBody.get("parameters");

            String response = chatService.chatWithPredefinedTemplate(request, templateType, parameters);
            return ResponseEntity.ok(Map.of(
                    "response", response,
                    "demo", "Predefined PromptTemplate from utility class",
                    "template_type", templateType,
                    "template_used", "Template from com.nhatdev.springai.utils.Prompt class",
                    "parameters_applied", parameters != null ? parameters : Map.of()
            ));
        } catch (Exception e) {
            logger.error("Error in predefined template demo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Demo 5: Dynamic PromptTemplate creation
     * 
     * Example request:
     * POST /api/chat/demo/dynamic
     * {
     *   "message": "Can you elaborate on that point?",
     *   "conversationPhase": "followup",
     *   "previousResponses": ["Microservices are distributed systems...", "They offer scalability benefits..."]
     * }
     */
    @PostMapping("/demo/dynamic")
    public ResponseEntity<?> demoDynamicTemplate(
            @RequestBody Map<String, Object> requestBody) {
        try {
            ChatRequest request = new ChatRequest((String) requestBody.get("message"), null);
            String conversationPhase = (String) requestBody.getOrDefault("conversationPhase", "greeting");
            @SuppressWarnings("unchecked")
            List<String> previousResponses = (List<String>) requestBody.get("previousResponses");

            String response = chatService.chatWithDynamicTemplate(request, conversationPhase, previousResponses);
            return ResponseEntity.ok(Map.of(
                    "response", response,
                    "demo", "Dynamic PromptTemplate based on conversation flow",
                    "conversation_phase", conversationPhase,
                    "template_used", "Dynamically built template for conversation phase",
                    "previous_responses_count", previousResponses != null ? previousResponses.size() : 0
            ));
        } catch (Exception e) {
            logger.error("Error in dynamic template demo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Get available template types for predefined templates demo
     */
    @GetMapping("/demo/template-types")
    public ResponseEntity<?> getAvailableTemplateTypes() {
        try {
            Map<String, String> templateTypes = Map.of(
                    "system", "Basic system prompt with role and guidelines",
                    "conversation", "Template for maintaining conversation context",
                    "greeting", "Personalized greeting template",
                    "code", "Template for programming assistance",
                    "problem", "Multi-step problem solving template",
                    "knowledge", "Knowledge base query template",
                    "troubleshooting", "Error diagnosis and solution template",
                    "tutorial", "Educational content creation template"
            );

            return ResponseEntity.ok(Map.of(
                    "available_templates", templateTypes,
                    "usage", "Use these template types in the /demo/predefined endpoint",
                    "note", "Each template has specific parameter requirements for optimal results"
            ));
        } catch (Exception e) {
            logger.error("Error getting template types: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving template types");
        }
    }

    /**
     * Get demo endpoints information
     */
    @GetMapping("/demo/info")
    public ResponseEntity<?> getDemoInfo() {
        try {
            Map<String, Object> demoInfo = Map.of(
                    "title", "PromptTemplate Demo Endpoints",
                    "description", "Demonstrates various ways to use PromptTemplate in Spring AI",
                    "endpoints", Map.of(
                            "/demo/basic", "Basic PromptTemplate with simple placeholders",
                            "/demo/multi-message", "Multi-message templates (System + User)",
                            "/demo/complex", "Complex templates with conditional content",
                            "/demo/predefined", "Predefined templates from utility class",
                            "/demo/dynamic", "Dynamic template creation based on conversation flow"
                    ),
                    "features_demonstrated", List.of(
                            "Simple placeholder replacement",
                            "Multi-message prompt construction",
                            "Conditional template content",
                            "Template reusability",
                            "Dynamic template building",
                            "Context-aware responses",
                            "Parameter validation and defaults"
                    ),
                    "template_benefits", List.of(
                            "Reusable prompt structures",
                            "Dynamic content generation",
                            "Consistent formatting",
                            "Parameter validation",
                            "Context preservation",
                            "Maintainable prompt management"
                    )
            );

            return ResponseEntity.ok(demoInfo);
        } catch (Exception e) {
            logger.error("Error getting demo info: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving demo information");
        }
    }
}