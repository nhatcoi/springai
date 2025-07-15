package com.nhatdev.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.dto.ChatRequest;

import java.util.List;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient chatClient;

    @Autowired
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Handle chat request
     */
    public String chat(ChatRequest request) {

        SystemMessage sysMsg = new SystemMessage(
                "You are JackieAI, a fun assistant. Answer the user's questions to the best of your ability by a fun way.");

        UserMessage userMsg = new UserMessage(request.getMessage());

        Prompt prompt = new Prompt(List.of(
                sysMsg,
                userMsg
        ));

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}