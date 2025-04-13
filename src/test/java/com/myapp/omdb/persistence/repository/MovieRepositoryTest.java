package com.myapp.omdb.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.TestPropertySource;

import com.myapp.omdb.persistence.entity.Movie;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setUp() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/test-movies.sql"));
        }
    }
    
    @AfterEach
    public void tearDown() throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP ALL OBJECTS DELETE FILES");
        }
    }

    @Test
    void shouldReturnAllMoviesFromDbOrderByYear() {
        List<Movie> movies = movieRepository.findAllByOrderByYearAsc();
        assertNotNull(movies, "Movies list should not be null");
        assertFalse(movies.isEmpty(), "Movies list should not be empty");

        List<Movie> expectedMovies = Arrays.asList(
            new Movie("tt0241527", "Harry Potter and the Sorcerer's Stone", "2001", "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"),
            new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002", "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg"),
            new Movie("tt0304141", "Harry Potter and the Prisoner of Azkaban", "2004", "https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg"),
            new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011", "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg")
        );

        assertEquals(expectedMovies.size(), movies.size(), "Should return correct number of movies");
        
        for (int i = 0; i < movies.size(); i++) {
            Movie actual = movies.get(i);
            Movie expected = expectedMovies.get(i);
            assertEquals(expected.getTitle(), actual.getTitle(), "Movie titles should match");
            assertEquals(expected.getYear(), actual.getYear(), "Movie years should match");
            assertEquals(expected.getImdbId(), actual.getImdbId(), "Movie IDs should match");
            assertEquals(expected.getImage(), actual.getImage(), "Movie images should match");
        }
    }

    @Test
    void shouldReturnMoviesContainingTitle() {
        String mockTitle = "Chamber";
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(mockTitle);
        
        assertNotNull(movies, "Result should not be null");
        assertEquals(1, movies.size(), "Should return exactly one movie");
        
        Movie movie = movies.get(0);
        assertEquals("tt0295297", movie.getImdbId(), "Should return correct movie ID");
        assertTrue(movie.getTitle().contains(mockTitle), "Title should contain search term");
    }

    @Test
    void shouldReturnMoviesByYear() {
        String mockYear = "2002";
        List<Movie> movies = movieRepository.findByYear(mockYear);
        
        assertNotNull(movies, "Result should not be null");
        assertEquals(1, movies.size(), "Should return exactly one movie");
        
        Movie movie = movies.get(0);
        assertEquals(mockYear, movie.getYear(), "Should return correct year");
        assertEquals("tt0295297", movie.getImdbId(), "Should return correct movie ID");
    }

    @Test
    void shouldReturnMoviesByPersonalRating() {
        int mockPersonalRating = 0;
        List<Movie> movies = movieRepository.findByPersonalRating(mockPersonalRating);
        
        assertNotNull(movies, "Result should not be null");
        assertEquals(4, movies.size(), "Should return correct number of movies");
        
        movies.forEach(movie -> 
            assertEquals(mockPersonalRating, movie.getPersonalRating(), 
                "All movies should have the specified rating")
        );
    }

    @Test
    void shouldUpdatePersonalRatingInDatabase() {
        // Given
        Movie mockMovie = new Movie();
        mockMovie.setImdbId("id1");
        mockMovie.setTitle("Harry Potter mock movie");
        mockMovie.setYear("2023");
        mockMovie.setPersonalRating(4);
        movieRepository.save(mockMovie);

        // When
        movieRepository.updatePersonalRating("id1", 2);

        // Then
        Optional<Movie> updatedMovieOptional = movieRepository.findById("id1");
        assertTrue(updatedMovieOptional.isPresent(), "Movie should exist in database");
        
        Movie updatedMovie = updatedMovieOptional.get();
        assertEquals(2, updatedMovie.getPersonalRating(), "Rating should be updated");
        assertEquals("id1", updatedMovie.getImdbId(), "ID should remain unchanged");
        assertEquals("Harry Potter mock movie", updatedMovie.getTitle(), "Title should remain unchanged");
    }

    @Test
    void shouldHandleInvalidMovieId() {
        Optional<Movie> movie = movieRepository.findById("nonexistent");
        assertFalse(movie.isPresent(), "Should return empty for non-existent ID");
    }

    @Test
    void shouldHandleEmptySearchResults() {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase("NonexistentTitle");
        assertNotNull(movies, "Result should not be null");
        assertTrue(movies.isEmpty(), "Should return empty list for no matches");
    }
}