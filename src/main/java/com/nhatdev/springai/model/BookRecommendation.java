package com.nhatdev.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * POJO cho book recommendation structured output
 */
@JsonPropertyOrder({"genre", "books", "reason"})
public record BookRecommendation(
    String genre,
    List<String> books,
    String reason
) {
} 