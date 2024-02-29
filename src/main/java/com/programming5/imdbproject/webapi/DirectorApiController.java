package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.dto.MovieDto;
import com.programming5.imdbproject.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorApiController {

    private final DirectorService directorService;

    public DirectorApiController(DirectorService directorService) {
        this.directorService = directorService;
    }

    // /api/directors/{id}/movies
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDto>> getDirectorMovies(@PathVariable("id") Integer id) {

        List<Movie> movies = directorService.getMoviesForDirectorById(id);

        if (movies == null) {
            return ResponseEntity.notFound().build();
        }

        List<MovieDto> movieDtos = movies
                .stream()
                .map(MovieDto::fromDomain)
                .toList();

        return ResponseEntity.ok(movieDtos);
    }

    // /api/directors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable("id") Integer id) {
        directorService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
