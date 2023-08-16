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

    // Using omdbService we will get the harry potter movies in movie model format and we store them in the local
    // database with movieRepository
    public void fetchAndSaveMoviesInDB() {
        List<Movie> movies = omdbService.fetchAndTransformHarryPotterMovies();
        movieRepository.saveAll(movies);
    }

    public List<Movie> getAllOrderedByYear() {
        return movieRepository.findAllByOrderByYearAsc();
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchByYear(String year) {
        return movieRepository.findByYear(year);
    }

    public List<Movie> searchByPersonalRating(Integer personalRating) {
        if (personalRating == null) {
            return movieRepository.findAllByOrderByYearAsc();
        }
        return movieRepository.findByPersonalRating(personalRating);
    }


    public void updatePersonalRating(String id, Integer personalRating) {
        movieRepository.updatePersonalRating(id, personalRating);
    }


}
