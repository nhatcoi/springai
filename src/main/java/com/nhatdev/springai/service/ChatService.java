package com.nhatdev.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient chatClient;

    @Autowired
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Original chat method (keeping for backward compatibility)
     */
    public String chat(ChatRequest request) {
        SystemMessage sysMsg = new SystemMessage(
                "You are JackieAI, a fun assistant. Answer the user's questions to the best of your ability by a fun way.");

        UserMessage userMsg = new UserMessage(request.getMessage());

        Prompt prompt = new Prompt(List.of(sysMsg, userMsg));

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * Demo 1: Basic PromptTemplate usage
     * Demonstrates simple placeholder replacement
     */
    public String chatWithBasicTemplate(ChatRequest request, String assistantName, String tone) {
        logger.info("Demo 1: Basic PromptTemplate usage");

        // Create template with placeholders
        String templateText = """
                You are {assistantName}, a helpful AI assistant.
                Please respond in a {tone} tone.
                
                User question: {question}
                
                Provide a helpful and accurate response.
                """;

        // Create PromptTemplate and render with values
        PromptTemplate promptTemplate = new PromptTemplate(templateText);
        
        Map<String, Object> templateValues = Map.of(
                "assistantName", assistantName != null ? assistantName : "JackieAI",
                "tone", tone != null ? tone : "friendly and professional",
                "question", request.getMessage()
        );

        Prompt prompt = promptTemplate.create(templateValues);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * Demo 2: Multi-message PromptTemplate
     * Shows how to create prompts with system and user messages using templates
     */
    public String chatWithMultiMessageTemplate(ChatRequest request, String userRole, String taskType) {
        logger.info("Demo 2: Multi-message PromptTemplate");

        // System message template
        String systemTemplate = """
                You are JackieAI, an expert assistant specialized in {taskType}.
                You are helping a {userRole} solve their problems.
                
                Guidelines:
                - Provide clear, actionable advice
                - Use examples when helpful
                - Be encouraging and supportive
                """;

        // User message template
        String userTemplate = """
                Context: I am a {userRole} working on {taskType} related tasks.
                Question: {question}
                
                Please help me understand this better and provide practical solutions.
                """;

        // Create templates
        PromptTemplate systemPromptTemplate = new PromptTemplate(systemTemplate);
        PromptTemplate userPromptTemplate = new PromptTemplate(userTemplate);

        Map<String, Object> templateValues = Map.of(
                "userRole", userRole != null ? userRole : "developer",
                "taskType", taskType != null ? taskType : "programming",
                "question", request.getMessage()
        );

        // Render templates
        SystemMessage systemMessage = new SystemMessage(
                systemPromptTemplate.render(templateValues)
        );
        UserMessage userMessage = new UserMessage(
                userPromptTemplate.render(templateValues)
        );

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * Demo 3: Complex PromptTemplate with conditional content
     * Demonstrates dynamic prompt building based on request parameters
     */
    public String chatWithComplexTemplate(ChatRequest request, Map<String, Object> contextData) {
        logger.info("Demo 3: Complex PromptTemplate with conditional content");

        String templateText = """
                You are {assistantName}, a {assistantRole} assistant.
                
                Context Information:
                - User Experience Level: {userLevel}
                - Domain: {domain}
                - Response Style: {responseStyle}
                {conditionalInstructions}
                
                User Request: {userRequest}
                
                Instructions:
                1. Tailor your response to the user's experience level
                2. Focus on the {domain} domain
                3. Use {responseStyle} communication style
                4. {additionalInstructions}
                """;

        // Build conditional instructions based on context
        String conditionalInstructions = buildConditionalInstructions(contextData);
        String additionalInstructions = buildAdditionalInstructions(contextData);

        PromptTemplate promptTemplate = new PromptTemplate(templateText);

        Map<String, Object> templateValues = Map.of(
                "assistantName", contextData.getOrDefault("assistantName", "JackieAI"),
                "assistantRole", contextData.getOrDefault("assistantRole", "helpful"),
                "userLevel", contextData.getOrDefault("userLevel", "intermediate"),
                "domain", contextData.getOrDefault("domain", "general"),
                "responseStyle", contextData.getOrDefault("responseStyle", "conversational"),
                "conditionalInstructions", conditionalInstructions,
                "userRequest", request.getMessage(),
                "additionalInstructions", additionalInstructions
        );

        Prompt prompt = promptTemplate.create(templateValues);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * Demo 4: PromptTemplate with predefined templates from utility class
     * Shows how to use templates from the Prompt utility class
     */
    public String chatWithPredefinedTemplate(ChatRequest request, String templateType, Map<String, Object> parameters) {
        logger.info("Demo 4: Using predefined templates - Type: {}", templateType);

        String templateText = getTemplateByType(templateType);
        PromptTemplate promptTemplate = new PromptTemplate(templateText);

        // Ensure required parameters are present with defaults
        Map<String, Object> safeParameters = ensureRequiredParameters(parameters, templateType);
        safeParameters.put("question", request.getMessage());
        safeParameters.put("userRequest", request.getMessage());

        Prompt prompt = promptTemplate.create(safeParameters);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * Demo 5: Dynamic PromptTemplate creation
     * Builds templates dynamically based on conversation flow
     */
    public String chatWithDynamicTemplate(ChatRequest request, String conversationPhase, List<String> previousResponses) {
        logger.info("Demo 5: Dynamic PromptTemplate - Phase: {}", conversationPhase);

        String templateText = buildDynamicTemplate(conversationPhase, previousResponses);
        PromptTemplate promptTemplate = new PromptTemplate(templateText);

        Map<String, Object> templateValues = Map.of(
                "conversationPhase", conversationPhase,
                "currentQuestion", request.getMessage(),
                "previousContext", buildPreviousContext(previousResponses),
                "assistantName", "JackieAI",
                "responseGuidance", getResponseGuidanceForPhase(conversationPhase)
        );

        Prompt prompt = promptTemplate.create(templateValues);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    // Helper methods for complex template building

    private String buildConditionalInstructions(Map<String, Object> contextData) {
        StringBuilder instructions = new StringBuilder();
        
        String userLevel = (String) contextData.getOrDefault("userLevel", "intermediate");
        if ("beginner".equals(userLevel)) {
            instructions.append("\n- Provide detailed explanations and avoid jargon");
        } else if ("advanced".equals(userLevel)) {
            instructions.append("\n- Use technical terminology and provide in-depth analysis");
        }

        String domain = (String) contextData.getOrDefault("domain", "general");
        if (!"general".equals(domain)) {
            instructions.append("\n- Focus specifically on ").append(domain).append(" concepts and practices");
        }

        return instructions.toString();
    }

    private String buildAdditionalInstructions(Map<String, Object> contextData) {
        Boolean includeExamples = (Boolean) contextData.getOrDefault("includeExamples", true);
        Boolean includeResources = (Boolean) contextData.getOrDefault("includeResources", false);

        StringBuilder instructions = new StringBuilder();
        if (includeExamples) {
            instructions.append("Include practical examples when relevant. ");
        }
        if (includeResources) {
            instructions.append("Suggest additional learning resources. ");
        }

        return instructions.length() > 0 ? instructions.toString() : "Provide a comprehensive response.";
    }

    private String getTemplateByType(String templateType) {
        return switch (templateType.toLowerCase()) {
            case "system" -> com.nhatdev.springai.utils.Prompt.SYSTEM_PROMPT_TEMPLATE;
            case "conversation" -> com.nhatdev.springai.utils.Prompt.CONVERSATION_CONTEXT_TEMPLATE;
            case "greeting" -> com.nhatdev.springai.utils.Prompt.GREETING_TEMPLATE;
            case "code" -> com.nhatdev.springai.utils.Prompt.CODE_ASSISTANCE_TEMPLATE;
            case "problem" -> com.nhatdev.springai.utils.Prompt.PROBLEM_SOLVING_TEMPLATE;
            case "knowledge" -> com.nhatdev.springai.utils.Prompt.KNOWLEDGE_QUERY_TEMPLATE;
            case "troubleshooting" -> com.nhatdev.springai.utils.Prompt.TROUBLESHOOTING_TEMPLATE;
            case "tutorial" -> com.nhatdev.springai.utils.Prompt.TUTORIAL_TEMPLATE;
            default -> """
                    You are {assistantName}, a helpful AI assistant.
                    Please answer the following question: {question}
                    Provide a clear and helpful response.
                    """;
        };
    }

    private Map<String, Object> ensureRequiredParameters(Map<String, Object> parameters, String templateType) {
        Map<String, Object> safeParams = new java.util.HashMap<>(parameters != null ? parameters : Map.of());
        
        // Add default values for common template parameters
        safeParams.putIfAbsent("assistantName", "JackieAI");
        safeParams.putIfAbsent("assistantRole", "helpful assistant");
        safeParams.putIfAbsent("teamName", "OggyTeam");
        safeParams.putIfAbsent("responseStyle", "friendly and professional");
        safeParams.putIfAbsent("tone", "helpful");
        safeParams.putIfAbsent("focusArea", "providing accurate information");

        // Template-specific defaults
        switch (templateType.toLowerCase()) {
            case "code" -> {
                safeParams.putIfAbsent("programmingLanguage", "General");
                safeParams.putIfAbsent("taskType", "Programming");
                safeParams.putIfAbsent("difficultyLevel", "Intermediate");
                safeParams.putIfAbsent("responseFormat", "Step-by-step");
            }
            case "problem" -> {
                safeParams.putIfAbsent("problemCategory", "General");
                safeParams.putIfAbsent("problemContext", "User inquiry");
                safeParams.putIfAbsent("numberOfSolutions", "3");
                safeParams.putIfAbsent("userExperienceLevel", "Intermediate");
            }
            case "tutorial" -> {
                safeParams.putIfAbsent("topic", "General Topic");
                safeParams.putIfAbsent("objective", "Learn and understand");
                safeParams.putIfAbsent("audience", "General audience");
                safeParams.putIfAbsent("timeLimit", "No specific limit");
                safeParams.putIfAbsent("numberOfSteps", "5");
                safeParams.putIfAbsent("teachingStyle", "Interactive and engaging");
            }
        }

        return safeParams;
    }

    private String buildDynamicTemplate(String conversationPhase, List<String> previousResponses) {
        return switch (conversationPhase.toLowerCase()) {
            case "greeting" -> """
                    Hello! I'm {assistantName}, your AI assistant.
                    Current question: {currentQuestion}
                    
                    {responseGuidance}
                    """;
            case "followup" -> """
                    Continuing our conversation...
                    
                    Previous context: {previousContext}
                    Current question: {currentQuestion}
                    
                    {responseGuidance}
                    """;
            case "clarification" -> """
                    Let me clarify based on our conversation.
                    
                    Previous discussion: {previousContext}
                    Clarification needed: {currentQuestion}
                    
                    {responseGuidance}
                    """;
            default -> """
                    I'm {assistantName}, here to help.
                    Question: {currentQuestion}
                    
                    {responseGuidance}
                    """;
        };
    }

    private String buildPreviousContext(List<String> previousResponses) {
        if (previousResponses == null || previousResponses.isEmpty()) {
            return "No previous context";
        }
        
        return "Previous responses: " + String.join(" | ", 
                previousResponses.stream()
                        .limit(3) // Limit to last 3 responses
                        .toList());
    }

    private String getResponseGuidanceForPhase(String conversationPhase) {
        return switch (conversationPhase.toLowerCase()) {
            case "greeting" -> "Provide a warm welcome and helpful response.";
            case "followup" -> "Build upon the previous conversation context.";
            case "clarification" -> "Focus on clarifying any confusion or ambiguity.";
            default -> "Provide a comprehensive and helpful response.";
        };
    }
}