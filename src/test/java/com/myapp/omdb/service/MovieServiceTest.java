package com.myapp.omdb.service;

import com.myapp.omdb.persistence.entity.Movie;
import com.myapp.omdb.persistence.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private OmdbService omdbService;

    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        // mocks initialization
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, omdbService);
    }


    @Test
    public void verify_all_movies_are_stored_in_db() {
        List<Movie> mockMovies = new ArrayList<>();
        // Movie simulated example to store in database
        mockMovies.add(new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011",
                "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"));
        when(omdbService.fetchAndTransformHarryPotterMovies()).thenReturn(mockMovies);

        // This will store the mock movies in db
        movieService.fetchAndSaveMoviesInDB();

        // This will verify that saveAll funciton was properly called and without errors
        verify(movieRepository).saveAll(mockMovies);

    }

}