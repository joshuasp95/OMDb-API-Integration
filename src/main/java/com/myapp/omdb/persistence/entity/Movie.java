package com.myapp.omdb.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Column(name = "`year`", nullable = false)
    private String year;

    private String image; // Could be null for movies without an image

    @Column(name = "personal_rating", columnDefinition = "int default 0")
    private Integer personalRating;

    public Movie(String imdbId, String title, String year, String image) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(imdbId, movie.imdbId) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(year, movie.year) &&
                Objects.equals(image, movie.image) &&
                Objects.equals(personalRating, movie.personalRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbId, title, year, image, personalRating);
    }
}
