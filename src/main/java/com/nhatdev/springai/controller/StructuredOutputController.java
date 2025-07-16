package com.nhatdev.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhatdev.springai.model.ActorsFilms;
import com.nhatdev.springai.model.BookRecommendation;
import com.nhatdev.springai.service.StructuredOutputService;

import java.util.List;
import java.util.Map;

/**
 * REST API Controller cho Spring AI Structured Output Converter Demo
 */
@RestController
@RequestMapping("/api/structured-output")
@CrossOrigin(origins = "*")
public class StructuredOutputController {

    private static final Logger logger = LoggerFactory.getLogger(StructuredOutputController.class);
    private final StructuredOutputService structuredOutputService;

    @Autowired
    public StructuredOutputController(StructuredOutputService structuredOutputService) {
        this.structuredOutputService = structuredOutputService;
    }

    /**
     * Demo BeanOutputConverter - Actor Films
     */
    @GetMapping("/actor-films")
    public ResponseEntity<ActorsFilms> getActorFilms(
            @RequestParam String actor) {
        try {
            logger.info("🎬 GET /api/structured-output/actor-films?actor={}", actor);
            
            ActorsFilms result = structuredOutputService.getActorFilms(actor);
            
            logger.info("✅ Successfully retrieved films for actor: {}", actor);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("❌ Error getting actor films: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Demo BeanOutputConverter - Book Recommendations
     */
    @GetMapping("/book-recommendations")
    public ResponseEntity<BookRecommendation> getBookRecommendations(
            @RequestParam String genre) {
        try {
            logger.info("📚 GET /api/structured-output/book-recommendations?genre={}", genre);
            
            BookRecommendation result = structuredOutputService.getBookRecommendations(genre);
            
            logger.info("✅ Successfully retrieved book recommendations for genre: {}", genre);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("❌ Error getting book recommendations: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Demo ListOutputConverter - Country List
     */
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getCountries(
            @RequestParam String continent) {
        try {
            logger.info("🌍 GET /api/structured-output/countries?continent={}", continent);
            
            List<String> result = structuredOutputService.getCountryList(continent);
            
            logger.info("✅ Successfully retrieved countries for continent: {}", continent);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("❌ Error getting countries: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Demo MapOutputConverter - Capital Info
     */
    @GetMapping("/capital-info")
    public ResponseEntity<Map<String, Object>> getCapitalInfo(
            @RequestParam String country) {
        try {
            logger.info("🏛️ GET /api/structured-output/capital-info?country={}", country);
            
            Map<String, Object> result = structuredOutputService.getCapitalInfo(country);
            
            logger.info("✅ Successfully retrieved capital info for country: {}", country);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("❌ Error getting capital info: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Demo endpoint - tổng hợp tất cả converters
     */
    @GetMapping("/demo-all")
    public ResponseEntity<Map<String, Object>> demoAll() {
        try {
            logger.info("🎯 GET /api/structured-output/demo-all - Running all converter demos");
            
            // Demo với data mẫu
            ActorsFilms actorFilms = structuredOutputService.getActorFilms("Tom Hanks");
            BookRecommendation bookRec = structuredOutputService.getBookRecommendations("science fiction");
            List<String> countries = structuredOutputService.getCountryList("Asia");
            Map<String, Object> capitalInfo = structuredOutputService.getCapitalInfo("Japan");
            
            Map<String, Object> allResults = Map.of(
                "beanConverter_ActorFilms", actorFilms,
                "beanConverter_BookRecommendation", bookRec,
                "listConverter_Countries", countries,
                "mapConverter_CapitalInfo", capitalInfo
            );
            
            logger.info("✅ All converter demos completed successfully");
            return ResponseEntity.ok(allResults);
            
        } catch (Exception e) {
            logger.error("❌ Error in demo-all: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Demo failed: " + e.getMessage()));
        }
    }

    /**
     * Info endpoint - Spring AI Structured Output Converter
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = Map.of(
            "title", "Spring AI Structured Output Converter Demo",
            "description", "Demonstrates BeanOutputConverter, ListOutputConverter, and MapOutputConverter",
            "documentation", "https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html",
            "endpoints", Map.of(
                "actor-films", "GET /api/structured-output/actor-films?actor={actor}",
                "book-recommendations", "GET /api/structured-output/book-recommendations?genre={genre}",
                "countries", "GET /api/structured-output/countries?continent={continent}",
                "capital-info", "GET /api/structured-output/capital-info?country={country}",
                "demo-all", "GET /api/structured-output/demo-all"
            ),
            "converters", Map.of(
                "BeanOutputConverter", "Converts LLM output to Java POJOs/Records",
                "ListOutputConverter", "Converts LLM output to Java Lists",
                "MapOutputConverter", "Converts LLM output to Java Maps"
            )
        );
        return ResponseEntity.ok(info);
    }
} 