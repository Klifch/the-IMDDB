package com.programming5.imdbproject.controller;


import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.service.MovieService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/show")
    public String showMovies(Model model) {

        List<Movie> movies = movieService.getAll();

        model.addAttribute("movies", movies);

        return "movies/show-movies";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showOneMovie(@PathVariable("id") Integer id, Model model) {

        // TODO: should return DTO to not expose the movie
        Movie movie = movieService.getById(id);

        model.addAttribute("movie", movie);

        return "/movies/single-movie";
    }

    @GetMapping("/search")
    public String searchMovies() {
        return "/movies/searchMovies";
    }


}
