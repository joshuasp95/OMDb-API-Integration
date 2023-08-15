package com.myapp.omdb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.omdb.persistence.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class OmdbServiceTest {
    private String omdbFullResponse;

    @Autowired
    private OmdbService omdbService;

    //Mock to simulate the communications of a rest api
    @MockBean
    private RestTemplate restTemplate;


    @BeforeEach
    public void setUp() {
        omdbFullResponse = "{\n" +
                "    \"Search\": [\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Deathly Hallows: Part 2\",\n" +
                "            \"Year\": \"2011\",\n" +
                "            \"imdbID\": \"tt1201607\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Sorcerer's Stone\",\n" +
                "            \"Year\": \"2001\",\n" +
                "            \"imdbID\": \"tt0241527\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Chamber of Secrets\",\n" +
                "            \"Year\": \"2002\",\n" +
                "            \"imdbID\": \"tt0295297\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Prisoner of Azkaban\",\n" +
                "            \"Year\": \"2004\",\n" +
                "            \"imdbID\": \"tt0304141\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Goblet of Fire\",\n" +
                "            \"Year\": \"2005\",\n" +
                "            \"imdbID\": \"tt0330373\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMTI1NDMyMjExOF5BMl5BanBnXkFtZTcwOTc4MjQzMQ@@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Order of the Phoenix\",\n" +
                "            \"Year\": \"2007\",\n" +
                "            \"imdbID\": \"tt0373889\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BOTA3MmRmZDgtOWU1Ny00ZDc5LWFkN2YtNzNlY2UxZmY0N2IyXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Deathly Hallows: Part 1\",\n" +
                "            \"Year\": \"2010\",\n" +
                "            \"imdbID\": \"tt0926084\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ2OTE1Mjk0N15BMl5BanBnXkFtZTcwODE3MDAwNA@@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Half-Blood Prince\",\n" +
                "            \"Year\": \"2009\",\n" +
                "            \"imdbID\": \"tt0417741\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNzU3NDg4NTAyNV5BMl5BanBnXkFtZTcwOTg2ODg1Mg@@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter 20th Anniversary: Return to Hogwarts\",\n" +
                "            \"Year\": \"2022\",\n" +
                "            \"imdbID\": \"tt16116174\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNTZkNWEyZTgtYzJlOS00OWNiLTgwZjMtZGU5NTRhNDNjOTRhXkEyXkFqcGdeQXVyNjk1Njg5NTA@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Forbidden Journey\",\n" +
                "            \"Year\": \"2010\",\n" +
                "            \"imdbID\": \"tt1756545\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNDM0YzMyNGUtMTU1Yy00OTE2LWE5NzYtZDZhMTBmN2RkNjg3XkEyXkFqcGdeQXVyMzU5NjU1MDA@._V1_SX300.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"totalResults\": \"134\",\n" +
                "    \"Response\": \"True\"\n" +
                "}";
    }

    @Test
    public void return_response_json_from_omdb_api_harry_potter_movies() throws IOException {

        //Mapper to convert json object to json node
        ObjectMapper mapper = new ObjectMapper();
        JsonNode mockResponse = mapper.readTree(omdbFullResponse);

        // This will set a simulated response from the api by the omdbService as the mock response, with any url as an argument
        when(restTemplate.getForObject(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.eq(JsonNode.class)))
                .thenReturn(mockResponse);

        // Call the method of the omdbService to process the mockResponse and verify that it will the same
        JsonNode serviceResponse = omdbService.fetchHarryPotterMovies();

        // Comparing both responses to see if there are any diferences between the expected response (mockResponse)
        // and the actual response (response)
        assertEquals(mockResponse, serviceResponse);
    }

    @Test
    public void transform_json_response_to_model_movie() throws IOException {
        String mockResponse = "{\n" +
                "    \"Search\": [\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Deathly Hallows: Part 2\",\n" +
                "            \"Year\": \"2011\",\n" +
                "            \"imdbID\": \"tt1201607\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Title\": \"Harry Potter and the Sorcerer's Stone\",\n" +
                "            \"Year\": \"2001\",\n" +
                "            \"imdbID\": \"tt0241527\",\n" +
                "            \"Type\": \"movie\",\n" +
                "            \"Poster\": \"https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg\"\n" +
                "        }]}";
        // Transform mock response to json node object
        JsonNode jsonResponse = new ObjectMapper().readTree(mockResponse);

        OmdbService omdbService = new OmdbService(restTemplate);

        // Transform movies in json response to movies entities
        List<Movie> movies = omdbService.transformJsonResponseToMovie(jsonResponse);

        assertEquals(2, movies.size());

        //Verify the properties from the first movie in the list
        Movie movie = movies.get(0);

        assertEquals("tt1201607", movie.getImdbId());
        assertEquals("Harry Potter and the Deathly Hallows: Part 2", movie.getTitle());
        assertEquals("2011", movie.getYear());
        assertEquals("https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg", movie.getImage());
    }

    @Test
    public void transform_all_harry_potter_movies_from_omdb_api_to_model_movies() throws IOException {

        // Same as the first test
        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.eq(JsonNode.class)))
                .thenReturn(new ObjectMapper().readTree(omdbFullResponse));

        OmdbService omdbService = new OmdbService(restTemplate);

        //Combination of both services: fetching the movies from the api and transforming them to movie entities. Stored as a list
        List<Movie> movies = omdbService.fetchAndTransformHarryPotterMovies();

        // There are 10 harry potter movies in the omdb api
        assertEquals(10, movies.size());
    }

}