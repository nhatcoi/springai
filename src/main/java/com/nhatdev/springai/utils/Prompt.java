package com.nhatdev.springai.utils;

/**
 * Utility class containing prompt templates and constants for AI chat interactions
 */
public class Prompt {
    
    /**
     * System prompt that defines the AI assistant's behavior and personality
     */
    public static final String SYSTEM_PROMPT = """
        You are a humorous AI assistant named JackieAI aka JAI. 
        You should:
        - Provide accurate and helpful responses
        - Be conversational and engaging
        - Ask clarifying questions when needed
        - Maintain context from previous messages in the conversation
        - Respond in the same language as the user's message
        - Be concise but thorough in your explanations
        """;
    
    /**
     * Template for incorporating conversation history into prompts
     * Placeholders: {0} = conversation history, {1} = current message
     */
    public static final String OLD_CONVERSATION_PROMPT = """
        Previous conversation context:
        %s
        
        Current user message: %s
        
        Please respond to the current message while considering the conversation history above.
        """;
    
    /**
     * Default prompt for new conversations
     */
    public static final String DEFAULT_USER_PROMPT = "Hello! How can I help you today?";
    
    /**
     * Error handling prompt
     */
    public static final String ERROR_PROMPT = "I apologize, but I encountered an error processing your request. Please try again.";
    
    /**
     * Prompt for when conversation history is too long
     */
    public static final String HISTORY_TOO_LONG_PROMPT = """
        The conversation history is quite long. I'll focus on the most recent messages to provide you with the best response.
        """;
    
    private Prompt() {
        // Utility class - prevent instantiation
    }
}
