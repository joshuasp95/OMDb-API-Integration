package com.myapp.omdb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myapp.omdb.persistence.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

// This service will get the response from omdb api and filter by their title (in this case Harry Potter films)
// Also it will be necessary to transform the json response containing the films to the movie model in this project
// which is the entity that will be stored in the mysql database
@Service
public class OmdbService {

    // Needed parameters to get the response from the omdb api, base url and api key
    @Value("${omdb.api.url}")
    private String API_URL;

    @Value("${omdb.api.key}")
    private String API_KEY;

    // To get the information from the api
    private final RestTemplate restTemplate;

    @Autowired
    public OmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected JsonNode fetchHarryPotterMovies() {
        //Request to the api with the selected parameters
        String omdbRequestUrl = API_URL + "?apikey=" + API_KEY + "&s=Harry Potter";

        //Json response
        return restTemplate.getForObject(omdbRequestUrl, JsonNode.class);
    }

    // Return all the harry potter movies as movie model entities
    public List<Movie> fetchAndTransformHarryPotterMovies() {
        JsonNode response = fetchHarryPotterMovies();
        return transformJsonResponseToMovie(response);
    }

    //Transform the result of omdb api response in JSON format to movie model
    protected List<Movie> transformJsonResponseToMovie(JsonNode omdbResponse) {

        List<Movie> movies = new ArrayList<>();

        // We need to process each node to transform each film to a movie entity
        if (omdbResponse != null && omdbResponse.has("Search")) {
            for (JsonNode movieNode : omdbResponse.get("Search")
            ) {
                Movie movie = new Movie();
                movie.setImdbId(movieNode.get("imdbID").asText());
                movie.setTitle(movieNode.get("Title").asText());
                movie.setYear(movieNode.get("Year").asText());
                movie.setImage(movieNode.get("Poster").asText());
                movie.setPersonalRating(0);//set to 0 as default value


                movies.add(movie);
            }
        }
        return movies;
    }


}