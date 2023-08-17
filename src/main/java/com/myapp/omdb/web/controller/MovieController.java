package com.myapp.omdb.web.controller;


import com.myapp.omdb.persistence.entity.Movie;
import com.myapp.omdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/movies")
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllOrderedByYear();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/searchByTitle")
    public String searchByTitle(@RequestParam("title") String title, Model model) {
        List<Movie> movies = movieService.searchByTitle(title);
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/searchByYear")
    public String searchByYear(@RequestParam("year") String year, Model model) {
        List<Movie> movies = movieService.searchByYear(year);
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/searchByRating")
    public String searchByRating(@RequestParam(name = "rating", required = false) Integer rating, Model model) {
        // null (empty) value introduced by client will return an empty table in movies.html
        if (rating == null) {
            return "movies";
        }
        List<Movie> movies = movieService.searchByPersonalRating(rating);
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/ratings")
    public String showRatingForm(Model model) {
        List<Movie> movies = movieService.getAllOrderedByYear();
        model.addAttribute("movies", movies);
        return "ratings";
    }

    //Still fixes needed to avoid malfunctions (null values, out of parameters bindings....)
    @PostMapping("/ratings")
    public String submitRatingForm(@RequestParam String id,
                                   @RequestParam Integer personalRating) {


        //Verify personal rating values are betweeen 1-5
        if (!validatePersonalRatingValues(personalRating)) {
            return "error";
        }
        movieService.updatePersonalRating(id, personalRating);
        return "redirect:/movies";
    }

    private boolean validatePersonalRatingValues(Integer personalRating) {
        return (personalRating >= 1 && personalRating <= 5) ? true : false;
    }

}
