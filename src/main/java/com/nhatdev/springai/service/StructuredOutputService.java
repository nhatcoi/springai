package com.nhatdev.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.model.ActorsFilms;
import com.nhatdev.springai.model.BookRecommendation;

import java.util.List;
import java.util.Map;

/**
 * Service demo Spring AI Structured Output Converter
 */
@Service
public class StructuredOutputService {

    private static final Logger logger = LoggerFactory.getLogger(StructuredOutputService.class);
    private final ChatClient chatClient;

    public StructuredOutputService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Demo BeanOutputConverter - Convert to POJO
     */
    public ActorsFilms getActorFilms(String actor) {
        logger.info("🎬 Getting films for actor: {}", actor);

        // Create BeanOutputConverter
        BeanOutputConverter<ActorsFilms> beanOutputConverter = 
            new BeanOutputConverter<>(ActorsFilms.class);

        // Create prompt template with format instructions
        String userMessage = """
            Generate the filmography for the actor {actor}.
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(userMessage);
        
        // Create prompt with format instructions from converter
        String prompt = promptTemplate.create(Map.of(
            "actor", actor,
            "format", beanOutputConverter.getFormat()
        )).getContents();

        logger.info("📝 Prompt with format instructions: {}", prompt);

        // Call LLM and convert response
        String response = chatClient
            .prompt(prompt)
            .call()
            .content();

        logger.info("🤖 LLM Response: {}", response);

        // Convert response to structured object
        assert response != null;
        ActorsFilms result = beanOutputConverter.convert(response);
        
        logger.info("✅ Converted to ActorsFilms: {}", result);
        return result;
    }

    /**
     * Demo BeanOutputConverter - Book recommendations
     */
    public BookRecommendation getBookRecommendations(String genre) {
        logger.info("📚 Getting book recommendations for genre: {}", genre);

        BeanOutputConverter<BookRecommendation> beanOutputConverter = 
            new BeanOutputConverter<>(BookRecommendation.class);

        String userMessage = """
            Recommend 3-5 great books in the {genre} genre.
            Include the reason why these books are recommended.
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(userMessage);
        
        String prompt = promptTemplate.create(Map.of(
            "genre", genre,
            "format", beanOutputConverter.getFormat()
        )).getContents();

        logger.info("📝 Prompt: {}", prompt);

        String response = chatClient
            .prompt(prompt)
            .call()
            .content();

        logger.info("🤖 Response: {}", response);

        BookRecommendation result = beanOutputConverter.convert(response);
        
        logger.info("✅ Converted to BookRecommendation: {}", result);
        return result;
    }

    /**
     * Demo ListOutputConverter - Simple list output
     */
    public List<String> getCountryList(String continent) {
        logger.info("🌍 Getting countries for continent: {}", continent);

        // Create ListOutputConverter with String element type
        ListOutputConverter listOutputConverter = new ListOutputConverter(
            new DefaultConversionService()
        );

        String userMessage = """
            List 5 countries in {continent}.
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(userMessage);
        
        String prompt = promptTemplate.create(Map.of(
            "continent", continent,
            "format", listOutputConverter.getFormat()
        )).getContents();

        logger.info("📝 Prompt: {}", prompt);

        String response = chatClient
            .prompt(prompt)
            .call()
            .content();

        logger.info("🤖 Response: {}", response);

        List<String> result = listOutputConverter.convert(response);
        
        logger.info("✅ Converted to List: {}", result);
        return result;
    }

    /**
     * Demo MapOutputConverter - Key-value output
     */
    public Map<String, Object> getCapitalInfo(String country) {
        logger.info("🏛️ Getting capital info for country: {}", country);

        MapOutputConverter mapOutputConverter = new MapOutputConverter();

        String userMessage = """
            Provide information about the capital city of {country}.
            Include: capital name, population, founded year, and famous landmarks.
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(userMessage);
        
        String prompt = promptTemplate.create(Map.of(
            "country", country,
            "format", mapOutputConverter.getFormat()
        )).getContents();

        logger.info("📝 Prompt: {}", prompt);

        String response = chatClient
            .prompt(prompt)
            .call()
            .content();

        logger.info("🤖 Response: {}", response);

        Map<String, Object> result = mapOutputConverter.convert(response);
        
        logger.info("✅ Converted to Map: {}", result);
        return result;
    }
} 