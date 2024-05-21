package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.dto.AddDirectorDto;
import com.programming5.imdbproject.dto.DirectorDto;
import com.programming5.imdbproject.dto.MovieDto;
import com.programming5.imdbproject.dto.PatchDirectorDto;
import com.programming5.imdbproject.service.DirectorService;
import com.programming5.imdbproject.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorApiController {

    private final DirectorService directorService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public DirectorApiController(
            DirectorService directorService,
            UserService userService,
            ModelMapper modelMapper
    ) {
        this.directorService = directorService;
        this.userService = userService;
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
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<DirectorDto> addDirector(
            @RequestBody @Valid AddDirectorDto addDirectorDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User creator = userService.findByUsername(authentication.getName());

        Director director = directorService.add(
                addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth(),
                addDirectorDto.nationality(),
                addDirectorDto.height(),
                creator
                );

        DirectorDto createdDirector = modelMapper.map(director, DirectorDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDirector);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_EDITOR') and @directorServiceImpl.canUserModify(principal.username, #id) or hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDirector(@PathVariable("id") Integer id) {
        directorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // IMPORTANT: I don't like how this works, for change in the future
    // THIS CODE DOES NOT WORK - was used in previous versions
    // NOW - for same purpose canModify!
    // DELETE for production(presentation) + DELETE: hideUpdateButtons.js

    @GetMapping("/checkPermission/{id}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> checkPermission(@PathVariable("id") Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Boolean hasPermission = directorService.canUserModify(authentication.getName(), id);

        Boolean isAdmin = userService.findByUsername(authentication.getName())
                .getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (hasPermission || isAdmin) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
