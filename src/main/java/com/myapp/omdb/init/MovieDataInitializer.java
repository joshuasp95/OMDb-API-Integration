package com.myapp.omdb.init;

import com.myapp.omdb.service.MovieService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MovieDataInitializer {
    private final MovieService movieService;


    public MovieDataInitializer(MovieService movieService) {
        this.movieService = movieService;
    }

    //To store the movies in the database each time application is started
    @PostConstruct
    public void init() {
        movieService.fetchAndSaveMoviesInDB();
    }
}
