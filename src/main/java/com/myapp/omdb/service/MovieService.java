package com.myapp.omdb.service;

import com.myapp.omdb.persistence.entity.Movie;
import com.myapp.omdb.persistence.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;

    @Autowired
    public MovieService(MovieRepository movieRepository, OmdbService omdbService) {
        this.movieRepository = movieRepository;
        this.omdbService = omdbService;
    }

    public void fetchAndSaveMoviesInDB() {
        List<Movie> movies = omdbService.fetchAndTransformHarryPotterMovies();

        movieRepository.saveAll(movies);
    }
}
