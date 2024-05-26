package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.repository.DirectorRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class DirectorServiceTest {

    @Autowired
    private DirectorService sut;

    @MockBean
    private DirectorRepository directorRepository;

    @MockBean
    private UserService userService;

    @Test
    void shouldPatchDirectorIfItExists() {
        // Arrange
        Director director = new Director(
                "John",
                "Doe",
                LocalDate.of(1980,8,8),
                180.8,
                "American"
        );
        director.setDirectorId(1);

        when(directorRepository.findById(1)).thenReturn(Optional.of(director));

        // Act
        Director patchedDirector = sut.patch(
                1,
                "Jane",
                null,
                null,
                "Mexican",
                null
        );

        // Assert
        assertEquals(1, patchedDirector.getDirectorId());
        assertEquals("Jane", patchedDirector.getFirstName());
        assertEquals("Doe", patchedDirector.getLastName());
        assertEquals("Mexican", patchedDirector.getNationality());

        verify(directorRepository).save(eq(patchedDirector));
    }

    @Test
    void shouldBeAbleToModifyIfCreator() {
        // Arrange
        User user = new User("john", "123");
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(directorRepository.existsByCreatorAndDirectorId(user, 1)).thenReturn(true);

        // Act
        Boolean canModify = sut.canUserModify(user.getUsername(), 1);

        // Assert
        assertTrue(canModify);
        verify(directorRepository, times(1)).existsByCreatorAndDirectorId(user, 1);
    }

    @Test
    void shouldNotBeAbleToModifyIfCreatorDoesNotExist() {
        // Arrange
        User user = new User("john", "123");
        when(userService.findByUsername("john")).thenReturn(null);

        // Act
        Boolean canModify = sut.canUserModify(user.getUsername(), 1);

        // Assert
        assertFalse(canModify);
        verify(directorRepository, never()).existsByCreatorAndDirectorId(user, 1);
    }

    @Test
    void shouldGetMoviesForDirector() {
        // Arrange
        Movie movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setMovieName("The Dark Knight");

        Director director = new Director();
        director.setDirectorId(1);
        director.setFirstName("John");
        director.setMovies(List.of(movie1));

        when(directorRepository.findById(director.getDirectorId()))
                .thenReturn(Optional.of(director));

        List<Movie> movies = sut.getMoviesForDirectorById(director.getDirectorId());

        // Assert
        assertEquals("The Dark Knight", movies.get(0).getMovieName());
        assertEquals(1, movies.get(0).getMovieId());
        assertEquals(1, movies.size());
    }



}
