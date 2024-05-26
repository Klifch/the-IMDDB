package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.repository.DirectorRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class DirectorServiceTest {

    @Autowired
    private DirectorService sut;

    @MockBean
    private DirectorRepository directorRepository;

    // TODO: TEST THIS ONE IT"S FUCKED UP
    @Test
    void shouldPatchDirectorIfItExists() throws IOException {
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

        verify(directorRepository).save(eq(patchedDirector));
    }



}
