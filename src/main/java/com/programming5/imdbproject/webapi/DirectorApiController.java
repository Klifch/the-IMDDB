package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.dto.AddDirectorDto;
import com.programming5.imdbproject.dto.DirectorDto;
import com.programming5.imdbproject.dto.MovieDto;
import com.programming5.imdbproject.dto.PatchDirectorDto;
import com.programming5.imdbproject.service.DirectorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorApiController {

    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    public DirectorApiController(DirectorService directorService, ModelMapper modelMapper) {
        this.directorService = directorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDto>> getDirectorMovies(@PathVariable("id") Integer id) {
        List<Movie> movies = directorService.getMoviesForDirectorById(id);

        if (movies == null) {
            return ResponseEntity.notFound().build();
        }

        // TODO: model mapper
        List<MovieDto> movieDtos = movies
                .stream()
                .map(MovieDto::fromDomain)
                .toList();

        return ResponseEntity.ok(movieDtos);
    }

    @PostMapping
    public ResponseEntity<DirectorDto> addDirector(@RequestBody @Valid AddDirectorDto addDirectorDto) {
        Director director = directorService.add(
                addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth(),
                addDirectorDto.nationality(),
                addDirectorDto.height()
                );

        DirectorDto createdDirector = modelMapper.map(director, DirectorDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDirector);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DirectorDto> updateFieldDirector(
            @RequestBody @Valid PatchDirectorDto patchDirectorDto,
            @PathVariable("id") Integer id
    ) {
        Director patchedDirector = directorService.patch(
                id,
                patchDirectorDto.firstName(),
                patchDirectorDto.lastName(),
                patchDirectorDto.dateOfBirth(),
                patchDirectorDto.nationality(),
                patchDirectorDto.height()
        );

        return ResponseEntity.ok(modelMapper.map(patchedDirector, DirectorDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable("id") Integer id) {
        directorService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
