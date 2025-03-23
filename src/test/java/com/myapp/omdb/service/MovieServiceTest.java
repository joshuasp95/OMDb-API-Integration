package com.myapp.omdb.service;

import com.myapp.omdb.persistence.entity.Movie;
import com.myapp.omdb.persistence.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private OmdbService omdbService;

    @Autowired
    private MovieService movieService;

    private List<Movie> harryPotterMovies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, omdbService);
        harryPotterMovies = new ArrayList<>();

        // Initialize test data
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
    @DisplayName("Should successfully fetch and save movies in database")
    void fetchAndSaveMoviesInDB_ShouldSaveAllMovies() {
        // Given
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011",
                "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"));
        when(omdbService.fetchAndTransformHarryPotterMovies()).thenReturn(mockMovies);

        // When
        movieService.fetchAndSaveMoviesInDB();

        // Then
        verify(movieRepository).saveAll(mockMovies);
        verify(omdbService).fetchAndTransformHarryPotterMovies();
    }

    @Test
    @DisplayName("Should handle empty movie list when saving to database")
    void fetchAndSaveMoviesInDB_ShouldHandleEmptyList() {
        // Given
        List<Movie> emptyMovies = new ArrayList<>();
        when(omdbService.fetchAndTransformHarryPotterMovies()).thenReturn(emptyMovies);

        // When
        movieService.fetchAndSaveMoviesInDB();

        // Then
        verify(movieRepository).saveAll(emptyMovies);
        assertTrue(emptyMovies.isEmpty(), "Movie list should be empty");
    }

    @Test
    @DisplayName("Should return all movies ordered by year")
    void getAllOrderedByYear_ShouldReturnSortedMovies() {
        // Given
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(new Movie("tt0241527", "Harry Potter and the Sorcerer's Stone", "2001",
                "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002",
                "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt0304141", "Harry Potter and the Prisoner of Azkaban", "2004",
                "https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg"));
        mockMovies.add(new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011",
                "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"));

        when(movieRepository.findAllByOrderByYearAsc()).thenReturn(mockMovies);

        // When
        List<Movie> actualMovies = movieService.getAllOrderedByYear();

        // Then
        assertNotNull(actualMovies, "Returned movies list should not be null");
        assertEquals(mockMovies.size(), actualMovies.size(), "Should return correct number of movies");
        assertEquals("2001", actualMovies.get(0).getYear(), "First movie should be from 2001");
        assertEquals("2011", actualMovies.get(3).getYear(), "Last movie should be from 2011");
        verify(movieRepository).findAllByOrderByYearAsc();
    }

    @Test
    @DisplayName("Should search movies by title")
    void searchByTitle_ShouldReturnMatchingMovies() {
        // Given
        String searchTitle = "Harry Potter";
        when(movieRepository.findByTitleContainingIgnoreCase(searchTitle)).thenReturn(harryPotterMovies);

        // When
        List<Movie> result = movieService.searchByTitle(searchTitle);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(harryPotterMovies.size(), result.size(), "Should return all matching movies");
        verify(movieRepository).findByTitleContainingIgnoreCase(searchTitle);
        
        // Verify all movies contain the search term
        result.forEach(movie -> 
            assertTrue(movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase()),
                "All movies should contain the search term")
        );
    }

    @Test
    @DisplayName("Should search movies by year")
    void searchByYear_ShouldReturnMoviesFromSpecifiedYear() {
        // Given
        String searchYear = "2001";
        List<Movie> expectedMovies = new ArrayList<>();
        expectedMovies.add(harryPotterMovies.get(0));
        when(movieRepository.findByYear(searchYear)).thenReturn(expectedMovies);

        // When
        List<Movie> result = movieService.searchByYear(searchYear);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return exactly one movie");
        assertEquals(searchYear, result.get(0).getYear(), "Movie should be from specified year");
        assertEquals("Harry Potter and the Sorcerer's Stone", result.get(0).getTitle(), "Should return correct movie");
        verify(movieRepository).findByYear(searchYear);
    }

    @Test
    @DisplayName("Should search movies by personal rating")
    void searchByPersonalRating_ShouldReturnMoviesWithSpecifiedRating() {
        // Given
        int searchRating = 0;
        when(movieRepository.findByPersonalRating(searchRating)).thenReturn(harryPotterMovies);

        // When
        List<Movie> result = movieService.searchByPersonalRating(searchRating);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(harryPotterMovies.size(), result.size(), "Should return all movies with specified rating");
        verify(movieRepository).findByPersonalRating(searchRating);
        
        // Verify all movies have the specified rating
        result.forEach(movie -> 
            assertEquals(searchRating, movie.getPersonalRating(),
                "All movies should have the specified rating")
        );
    }

    @Test
    @DisplayName("Should update personal rating for a movie")
    void updatePersonalRating_ShouldUpdateMovieRating() {
        // Given
        String movieId = "tt0241527";
        int newRating = 4;

        // When
        movieService.updatePersonalRating(movieId, newRating);

        // Then
        verify(movieRepository).updatePersonalRating(movieId, newRating);
    }

    @Test
    @DisplayName("Should handle invalid movie ID when updating rating")
    void updatePersonalRating_ShouldHandleInvalidMovieId() {
        // Given
        String invalidMovieId = "invalid_id";
        int newRating = 4;

        // When
        movieService.updatePersonalRating(invalidMovieId, newRating);

        // Then
        verify(movieRepository).updatePersonalRating(invalidMovieId, newRating);
    }

    @Test
    @DisplayName("Should handle empty search results")
    void searchByTitle_ShouldHandleEmptyResults() {
        // Given
        String searchTitle = "NonexistentMovie";
        when(movieRepository.findByTitleContainingIgnoreCase(searchTitle)).thenReturn(new ArrayList<>());

        // When
        List<Movie> result = movieService.searchByTitle(searchTitle);

        // Then
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Should return empty list for no matches");
        verify(movieRepository).findByTitleContainingIgnoreCase(searchTitle);
    }
}