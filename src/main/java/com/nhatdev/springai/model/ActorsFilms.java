package com.nhatdev.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * POJO cho demo BeanOutputConverter - theo Spring AI documentation
 */
@JsonPropertyOrder({"actor", "movies"})
public record ActorsFilms(String actor, List<String> movies) {
} 