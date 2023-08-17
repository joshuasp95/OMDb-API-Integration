package com.myapp.omdb.persistence.repository;

import com.myapp.omdb.persistence.entity.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setUp() throws SQLException {

        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql/test-movies.sql"));

    }

    @AfterEach
    public void tearDown() throws Exception {
        // Cleans up the database
        try (Statement st = dataSource.getConnection().createStatement()) {
            st.execute("DROP ALL OBJECTS DELETE FILES");
        }
    }

    @Test
    public void should_return_all_movies_from_db_order_by_year() {
        List<Movie> movies = movieRepository.findAllByOrderByYearAsc();

        List<Movie> expectedMovies = Arrays.asList(new Movie("tt0241527", "Harry Potter and the Sorcerer's Stone", "2001", "https://m.media-amazon.com/images/M/MV5BNmQ0ODBhMjUtNDRhOC00MGQzLTk5MTAtZDliODg5NmU5MjZhXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg"),
                new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002", "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg"),
                new Movie("tt0304141", "Harry Potter and the Prisoner of Azkaban", "2004", "https://m.media-amazon.com/images/M/MV5BMTY4NTIwODg0N15BMl5BanBnXkFtZTcwOTc0MjEzMw@@._V1_SX300.jpg"),
                new Movie("tt1201607", "Harry Potter and the Deathly Hallows: Part 2", "2011", "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg"));

        for (int i = 0; i < movies.size(); i++) {
            //System.out.println(movies.get(i).getTitle());
            //System.out.println(expectedMovies.get(i).getTitle());
            assertEquals(movies.get(i).getTitle(), expectedMovies.get(i).getTitle());

        }

    }

    @Test
    public void should_return_movies_containing_title() {
        String mockTitle = "Chamber";
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(mockTitle);

        Movie mockMovie = new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002", "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg");

        assertEquals(1, movies.size());

        assertEquals(mockMovie.getTitle(), movies.get(0).getTitle());
    }

    @Test
    public void should_return_movies_by_year() {
        String mockYear = "2002";

        List<Movie> movies = movieRepository.findByYear(mockYear);

        Movie mockMovie = new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002", "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg");

        assertEquals(1, movies.size());

        assertEquals(mockMovie.getTitle(), movies.get(0).getTitle());
    }

    @Test
    public void should_return_movies_by_personal_rating() {
        int mockPersonalRating = 0;

        List<Movie> movies = movieRepository.findByPersonalRating(mockPersonalRating);

        Movie mockMovie = new Movie("tt0295297", "Harry Potter and the Chamber of Secrets", "2002", "https://m.media-amazon.com/images/M/MV5BMjE0YjUzNDUtMjc5OS00MTU3LTgxMmUtODhkOThkMzdjNWI4XkEyXkFqcGdeQXVyMTA3MzQ4MTc0._V1_SX300.jpg");

        // all movies have a default value of 0
        assertEquals(4, movies.size());

    }

    @Test
    public void should_update_personal_rating_in_database() {

        Movie mockMovie = new Movie();
        mockMovie.setImdbId("id1");
        mockMovie.setTitle("Harry Potter mock movie");
        mockMovie.setYear("2023");
        mockMovie.setPersonalRating(4);

        movieRepository.save(mockMovie);

        movieRepository.updatePersonalRating("id1", 2);

        // optional needed to avoid null errors
        Optional<Movie> updatedMovieOptional = movieRepository.findById("id1");

        assertTrue(updatedMovieOptional.isPresent());

        Movie updatedMovie = updatedMovieOptional.get();

        // verify that personal rating value has been updated on h2 mem test db
        assertEquals(2, updatedMovie.getPersonalRating());

    }

}