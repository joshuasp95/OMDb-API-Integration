package com.myapp.omdb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.omdb.persistence.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OmdbServiceTest {
    private String omdbFullResponse;
    private ObjectMapper objectMapper;

    @Autowired
    private OmdbService omdbService;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${omdb.api.url}")
    private String apiUrl;

    @Value("${omdb.api.key}")
    private String apiKey;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        omdbFullResponse = "{\n" +
                "    \"Search\": [\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Deathly Hallows: Part 2\",\n" +
                "            \"Year\": \"2011\",\n" +
                "            \"imdbID\": \"tt1201607\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"totalResults\": \"1\",\n" +
                "    \"Response\": \"True\"\n" +
                "}";
    }

    @Test
    @DisplayName("Should successfully fetch Harry Potter movies from OMDB API")
    void fetchHarryPotterMovies_ShouldReturnValidResponse() throws IOException {
        // Given
        JsonNode mockResponse = objectMapper.readTree(omdbFullResponse);
        String expectedUrl = apiUrl + "?apikey=" + apiKey + "&s=Harry Potter";
        
        when(restTemplate.getForObject(expectedUrl, JsonNode.class))
                .thenReturn(mockResponse);

        // When
        JsonNode result = omdbService.fetchHarryPotterMovies();

        // Then
        assertNotNull(result, "Response should not be null");
        assertEquals(mockResponse, result, "Response should match mock data");
        verify(restTemplate).getForObject(expectedUrl, JsonNode.class);
    }

    @Test
    @DisplayName("Should handle API error response")
    void fetchHarryPotterMovies_ShouldHandleErrorResponse() throws IOException {
        // Given
        String errorResponse = "{\"Response\":\"False\",\"Error\":\"Invalid API key\"}";
        JsonNode mockResponse = objectMapper.readTree(errorResponse);
        
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenReturn(mockResponse);

        // When
        JsonNode result = omdbService.fetchHarryPotterMovies();

        // Then
        assertNotNull(result, "Response should not be null");
        assertEquals("False", result.get("Response").asText(), "Response should indicate error");
        assertTrue(result.has("Error"), "Response should contain error message");
    }

    @Test
    @DisplayName("Should transform JSON response to Movie entities")
    void transformJsonResponseToMovie_ShouldCreateValidMovies() throws IOException {
        // Given
        JsonNode jsonResponse = objectMapper.readTree(omdbFullResponse);

        // When
        List<Movie> movies = omdbService.transformJsonResponseToMovie(jsonResponse);

        // Then
        assertNotNull(movies, "Movies list should not be null");
        assertFalse(movies.isEmpty(), "Movies list should not be empty");
        assertEquals(1, movies.size(), "Should create correct number of movies");

        Movie movie = movies.get(0);
        assertEquals("tt1201607", movie.getImdbId(), "Should set correct IMDB ID");
        assertEquals("Harry Potter and the Deathly Hallows: Part 2", movie.getTitle(), "Should set correct title");
        assertEquals("2011", movie.getYear(), "Should set correct year");
        assertTrue(movie.getImage().startsWith("https://"), "Should set valid image URL");
    }

    @Test
    @DisplayName("Should handle empty search results")
    void transformJsonResponseToMovie_ShouldHandleEmptyResults() throws IOException {
        // Given
        String emptyResponse = "{\"Search\":[],\"totalResults\":\"0\",\"Response\":\"True\"}";
        JsonNode jsonResponse = objectMapper.readTree(emptyResponse);

        // When
        List<Movie> movies = omdbService.transformJsonResponseToMovie(jsonResponse);

        // Then
        assertNotNull(movies, "Movies list should not be null");
        assertTrue(movies.isEmpty(), "Movies list should be empty");
    }

    @Test
    @DisplayName("Should handle null response")
    void transformJsonResponseToMovie_ShouldHandleNullResponse() {
        // When
        List<Movie> movies = omdbService.transformJsonResponseToMovie(null);

        // Then
        assertNotNull(movies, "Movies list should not be null");
        assertTrue(movies.isEmpty(), "Movies list should be empty");
    }

    @Test
    @DisplayName("Should fetch and transform movies successfully")
    void fetchAndTransformHarryPotterMovies_ShouldReturnValidMovies() throws IOException {
        // Given
        JsonNode mockResponse = objectMapper.readTree(omdbFullResponse);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenReturn(mockResponse);

        // When
        List<Movie> movies = omdbService.fetchAndTransformHarryPotterMovies();

        // Then
        assertNotNull(movies, "Movies list should not be null");
        assertFalse(movies.isEmpty(), "Movies list should not be empty");
        assertEquals(1, movies.size(), "Should return correct number of movies");

        Movie movie = movies.get(0);
        assertEquals("tt1201607", movie.getImdbId(), "Should set correct IMDB ID");
        assertEquals("Harry Potter and the Deathly Hallows: Part 2", movie.getTitle(), "Should set correct title");
        assertEquals("2011", movie.getYear(), "Should set correct year");
    }

    @Test
    @DisplayName("Should handle API timeout")
    void fetchHarryPotterMovies_ShouldHandleTimeout() {
        // Given
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenThrow(new RuntimeException("Connection timeout"));

        // When/Then
        assertThrows(RuntimeException.class, () -> omdbService.fetchHarryPotterMovies());
        verify(restTemplate).getForObject(anyString(), eq(JsonNode.class));
    }
}