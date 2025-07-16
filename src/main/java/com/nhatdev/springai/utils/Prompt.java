package com.nhatdev.springai.utils;

/**
 * Template prompts for Spring AI PromptTemplate usage
 * Demonstrates different ways to structure prompts with placeholders
 */
public class Prompt {

    // Basic system prompt template
    public static final String SYSTEM_PROMPT_TEMPLATE = """
            You are {assistantName}, a {assistantRole} of {teamName}.
            Your task is to assist users by providing accurate and relevant information.
            Please respond in a {responseStyle} manner.
            
            Guidelines:
            - Be helpful and informative
            - Use appropriate tone: {tone}
            - Focus on: {focusArea}
            """;

    // Conversation context template
    public static final String CONVERSATION_CONTEXT_TEMPLATE = """
            Previous conversation context:
            {previousContext}
            
            Current question: {currentQuestion}
            
            Instructions:
            - Consider the previous context when answering
            - Maintain conversation continuity
            - Provide a {responseType} response
            """;

    // User greeting template with personalization
    public static final String GREETING_TEMPLATE = """
            Hello {userName}! Welcome to {applicationName}.
            
            I'm {assistantName}, your personal assistant. I can help you with:
            {availableServices}
            
            How can I assist you today?
            """;

    // Task-specific template for code assistance
    public static final String CODE_ASSISTANCE_TEMPLATE = """
            Programming Language: {programmingLanguage}
            Task Type: {taskType}
            Difficulty Level: {difficultyLevel}
            
            User Request: {userRequest}
            
            Please provide:
            1. A clear explanation
            2. Code example if applicable
            3. Best practices
            4. Common pitfalls to avoid
            
            Response format: {responseFormat}
            """;

    // Multi-step problem solving template
    public static final String PROBLEM_SOLVING_TEMPLATE = """
            Problem Category: {problemCategory}
            Context: {problemContext}
            
            User's Question: {question}
            
            Please follow this structure:
            1. Problem Analysis: Break down the issue
            2. Possible Solutions: List {numberOfSolutions} solutions
            3. Recommended Approach: Choose the best solution
            4. Implementation Steps: Provide step-by-step guide
            5. Validation: How to verify the solution works
            
            Consider user's experience level: {userExperienceLevel}
            """;

    // Knowledge base query template
    public static final String KNOWLEDGE_QUERY_TEMPLATE = """
            Knowledge Domain: {domain}
            Query Type: {queryType}
            
            Question: {question}
            
            Search Parameters:
            - Include related topics: {includeRelated}
            - Depth level: {depthLevel}
            - Include examples: {includeExamples}
            
            Format the response as {outputFormat}
            """;

    // Error handling and troubleshooting template
    public static final String TROUBLESHOOTING_TEMPLATE = """
            System: {systemType}
            Error Category: {errorCategory}
            
            Error Description: {errorDescription}
            Steps Already Tried: {stepsTried}
            
            Environment Details:
            - OS: {operatingSystem}
            - Version: {version}
            - Configuration: {configuration}
            
            Please provide:
            1. Root cause analysis
            2. Step-by-step solution
            3. Prevention strategies
            4. Additional resources
            """;

    // Learning and tutorial template
    public static final String TUTORIAL_TEMPLATE = """
            Topic: {topic}
            Learning Objective: {objective}
            Target Audience: {audience}
            Time Available: {timeLimit}
            
            Learning Request: {learningRequest}
            
            Please create a tutorial with:
            1. Prerequisites: {prerequisites}
            2. Learning Path: Break into {numberOfSteps} steps
            3. Practical Examples: Include {exampleType} examples
            4. Practice Exercises: {exerciseType}
            5. Additional Resources: {resourceTypes}
            
            Teaching Style: {teachingStyle}
            """;

    // Deprecated - keeping for backward compatibility
    @Deprecated
    public static final String OLD_CONVERSATION_PROMPT = """
            This is the previous conversation:
            %s
            
            Now please answer the following question: %s
            
            Please respond in a natural, friendly, and helpful manner.
            If necessary, you may refer to the context from the previous conversation.
            """;

    @Deprecated
    public static final String SYSTEM_PROMPT = """
            You are JackieAI, a helpful assistant of OggyTeam.
            Your task is to assist users by providing accurate and relevant information.
            Please respond in a natural, friendly, and helpful manner.
            If necessary, you may refer to the context from the previous conversation.
            """;
}
