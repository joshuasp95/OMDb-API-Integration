package com.myapp.omdb.persistence.repository;

import com.myapp.omdb.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Basic Crud Operations (Spring Data JPA)
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByYear(String year);

    List<Movie> findByPersonalRating(int personalRating);

    List<Movie> findAllByOrderByYearAsc();

    @Query(value = "UPDATE movie SET personal_rating = :#{#personalRating} WHERE imdb_id = :#{#id}", nativeQuery = true)
    @Modifying
    @Transactional
    void updatePersonalRating(@Param("id") String id, @Param("personalRating") Integer personalRating);

}
