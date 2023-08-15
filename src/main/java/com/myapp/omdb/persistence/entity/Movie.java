package com.myapp.omdb.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @Column(name = "imdb_id", nullable = false)
    private String imdbId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String year;

    private String image; // Could be null for movies without an image

    @Column(name = "personal_rating")
    private Integer personalRating;

}
