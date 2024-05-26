package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.dto.MovieDto;
import com.programming5.imdbproject.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieApiController {

    private final MovieService movieService;

    public MovieApiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam("searchTerm") String searchTerm) {
        List<MovieDto> foundMovies = movieService
                .search(searchTerm)
                .stream()
                .map(MovieDto::fromDomain)
                .toList();
        return ResponseEntity.ok(foundMovies);
    }


}
