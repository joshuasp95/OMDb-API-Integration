package com.myapp.omdb.persistence.repository;

import com.myapp.omdb.persistence.entity.Movie;
import org.springframework.data.repository.ListCrudRepository;

// Basic Crud Operations (Spring Data JPA)
public interface MovieRepository extends ListCrudRepository<Movie, String> {

}
