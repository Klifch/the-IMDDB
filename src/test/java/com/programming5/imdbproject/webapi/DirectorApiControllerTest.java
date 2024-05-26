package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.dto.AddDirectorDto;
import com.programming5.imdbproject.service.DirectorService;
import com.programming5.imdbproject.service.UserService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class DirectorApiControllerTest {

    @Autowired
    private DirectorApiController sut;

    @MockBean
    private DirectorService directorService;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityContext securityContextHolder;

    // TODO: TEST THIS ONE IT"S FUCKED UP
    @Test
    @WithMockUser(username = "editor", roles = {"EDITOR"})
    void shouldAddDirectorIfDoesNotExist() throws Exception {
        // Arrange
        AddDirectorDto addDirectorDto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980,8,8),
                "American",
                180.8
        );

        Director director = new Director(
                "John",
                "Doe",
                LocalDate.of(1980,8,8),
                180.8,
                "American"
        );
        director.setDirectorId(1);

        User user = new User("Jane", "123");
        when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        when(directorService.alreadyExists(
                addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth()
        )).thenReturn(false);
        when(directorService.add(
                eq(addDirectorDto.firstName()),
                eq(addDirectorDto.lastName()),
                eq(addDirectorDto.dateOfBirth()),
                eq(addDirectorDto.nationality()),
                eq(addDirectorDto.height()),
                eq(user)
        )).thenReturn(director);

        // Act
        var response = sut.addDirector(addDirectorDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody().getId(), director.getDirectorId());
        verify(directorService).add(addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth(),
                addDirectorDto.nationality(),
                addDirectorDto.height(),
                user);


    }

    // TODO: TEST THIS ONE IT"S FUCKED UP
    @Test
    @WithMockUser(username = "editor", roles = {"EDITOR"})
    void shouldNotAddDirectorIfAlreadyExists() throws Exception {
        // Arrange
        AddDirectorDto addDirectorDto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980,8,8),
                "American",
                180.8
        );

        Director director = new Director(
                "John",
                "Doe",
                LocalDate.of(1980,8,8),
                180.8,
                "American"
        );
        director.setDirectorId(1);

        User user = new User("Jane", "123");
        when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        when(directorService.alreadyExists(
                addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth()
        )).thenReturn(true);

        // Act
        var response = sut.addDirector(addDirectorDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(directorService, never()).add(addDirectorDto.firstName(),
                addDirectorDto.lastName(),
                addDirectorDto.dateOfBirth(),
                addDirectorDto.nationality(),
                addDirectorDto.height(),
                user);


    }



}
