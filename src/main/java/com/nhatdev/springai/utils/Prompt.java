package com.nhatdev.springai.utils;

public class Prompt {

    public static final String OLD_CONVERSATION_PROMPT = """
            This is the previous conversation:
            %s
            
            Now please answer the following question: %s
            
            Please respond in a natural, friendly, and helpful manner.
            If necessary, you may refer to the context from the previous conversation.
            """;

}
