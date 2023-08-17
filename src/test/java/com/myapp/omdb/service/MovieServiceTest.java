package com.myapp.omdb.service;

import com.myapp.omdb.persistence.entity.Movie;
import com.myapp.omdb.persistence.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private OmdbService omdbService;

    @Autowired
    private MovieService movieService;

    private List<Movie> harryPotterMovies;

    @BeforeEach
    public void setUp() {
        // mocks initialization
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, omdbService);
        harryPotterMovies = new ArrayList<>();

        harryPotterMovies.add(new Movie(
                "tt0241527",
                "Harry Potter and the Sorcerer's Stone",
                "2001",
                "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0295297",
                "Harry Potter and the Chamber of Secrets",
                "2002",
                "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0304141",
                "Harry Potter and the Prisoner of Azkaban",
                "2004",
                "https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0330373",
                "Harry Potter and the Goblet of Fire",
                "2005",
                "https://m.media-amazon.com/images/M/MV5BMTI1NDMyMjExOF5BMl5BanBnXkFtZTcwOTc4MjQzMQ@@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0373889",
                "Harry Potter and the Order of the Phoenix",
                "2007",
                "https://m.media-amazon.com/images/M/MV5BOTA3MmRmZDgtOWU1Ny00ZDc5LWFkN2YtNzNlY2UxZmY0N2IyXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0417741",
                "Harry Potter and the Half-Blood Prince",
                "2009",
                "https://m.media-amazon.com/images/M/MV5BNzU3NDg4NTAyNV5BMl5BanBnXkFtZTcwOTg2ODg1Mg@@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt0926084",
                "Harry Potter and the Deathly Hallows: Part 1",
                "2010",
                "https://m.media-amazon.com/images/M/MV5BMTQ2OTE1Mjk0N15BMl5BanBnXkFtZTcwODE3MDAwNA@@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt1201607",
                "Harry Potter and the Deathly Hallows: Part 2",
                "2011",
                "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt16116174",
                "Harry Potter 20th Anniversary: Return to Hogwarts",
                "2022",
                "https://m.media-amazon.com/images/M/MV5BNTZkNWEyZTgtYzJlOS00OWNiLTgwZjMtZGU5NTRhNDNjOTRhXkEyXkFqcGdeQXVyNjk1Njg5NTA@._V1_SX300.jpg"
        ));

        harryPotterMovies.add(new Movie(
                "tt1756545",
                "Harry Potter and the Forbidden Journey",
                "2010",
                "https://m.media-amazon.com/images/M/MV5BNDM0YzMyNGUtMTU1Yy00OTE2LWE5NzYtZDZhMTBmN2RkNjg3XkEyXkFqcGdeQXVyMzU5NjU1MDA@._V1_SX300.jpg"
        ));

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

        // This will verify that saveAll function was properly called and without errors
        verify(movieRepository).saveAll(mockMovies);

    }

    @Test
    public void return_all_movies_from_db_order_by_year() {
        List<Movie> mockMovies = new ArrayList<>();

        // Movies simulated example returned from database
        mockMovies.add(new Movie("tt0241527", "Harry Potter and the Sorcerer's Stone", "2001",
                "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002",
                "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt0304141", "Harry Potter and the Prisoner of Azkaban", "2004",
                "https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011",
                "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"));

        when(movieRepository.findAllByOrderByYearAsc()).thenReturn(mockMovies);

        List<Movie> actualMovies = movieService.getAllOrderedByYear();

        assertEquals(mockMovies, actualMovies);
        assertEquals("2011", actualMovies.get(3).getYear());
        assertEquals(mockMovies.size(), actualMovies.size());

    }

    @Test
    public void return_movies_containing_title() {
        // All harry potter movies will go through movieRepository
        List<Movie> mockMovies = harryPotterMovies;
        when(movieRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(mockMovies);

        //All movies must contain this title
        List<Movie> result = movieService.searchByTitle("Harry Potter");

        //There are 10 movies in the database
        assertEquals(10, result.size());

        // Validate that method has been called
        verify(movieRepository).findByTitleContainingIgnoreCase("Harry Potter");

        assertEquals(mockMovies, result);
    }

    @Test
    public void return_movies_by_year() {
        // First harry potter movie
        String mockYear = "2001";

        // Added to the mock movies response by repository
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(harryPotterMovies.get(0));

        when(movieRepository.findByYear(mockYear)).thenReturn(mockMovies);

        List<Movie> result = movieService.searchByYear(mockYear);

        assertEquals(1, result.size());
        assertEquals("2001", result.get(0).getYear());
        assertEquals("Harry Potter and the Sorcerer's Stone", result.get(0).getTitle());
    }

    @Test
    public void return_movies_by_personal_rating() {

        int mockPersonalRating = 0;

        List<Movie> mockMovies = harryPotterMovies;

        when(movieRepository.findByPersonalRating(mockPersonalRating)).thenReturn(mockMovies);

        List<Movie> result = movieService.searchByPersonalRating(mockPersonalRating);

        // all movies should have a 0 value for personal rating
        assertEquals(10, result.size());
        assertEquals(mockMovies.get(1).getYear(), result.get(1).getYear());
    }

    @Test
    public void verify_update_function_from_movie_repository_has_been_called() {

        Movie mockMovie = new Movie("tt0241527", "Harry Potter and the Sorcerer's Stone", "2001",
                "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg");

        mockMovie.setPersonalRating(4);

        movieRepository.updatePersonalRating(mockMovie.getImdbId(), mockMovie.getPersonalRating());

        verify(movieRepository).updatePersonalRating(mockMovie.getImdbId(), mockMovie.getPersonalRating());

    }

}