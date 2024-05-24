package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.repository.DirectorRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class DirectorServiceTest {

    @Autowired
    DirectorService sut;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    UserService userService;

    @Test
    void shouldNotReturnMoviesForDirector() {
        // Act
        List<Movie> movies = sut.getMoviesForDirectorById(1000);

        // Assert
        assertNull(movies);
    }

    @Test
    void shouldSaveAndReturn() {
        // Arrange
        String firstName1 = "John";
        String firstName2 = "Jane";
        String lastName = "Doe";
        LocalDate dob = LocalDate.of(1980, 8, 8);
        Double height = 188.8;
        String nationality = "American";
        User creator = userService.findByUsername("sam");

        // Act
        Director firstDirector = sut.add(
                firstName1,
                lastName,
                dob,
                nationality,
                height,
                creator
        );

        Director secondDirector = sut.add(
                firstName2,
                lastName,
                dob,
                null,
                null,
                creator
        );

        // Assert
        assertNotNull(firstDirector);
        assertNotNull(secondDirector);
        assertEquals(firstName1, firstDirector.getFirstName());
        assertEquals(firstName2, secondDirector.getFirstName());
        assertNotNull(firstDirector.getHeight());
        assertNull(secondDirector.getHeight());
        assertNull(secondDirector.getNationality());
        assertNotNull(firstDirector.getDirectorId());
        assertNotNull(secondDirector.getDirectorId());

        directorRepository.delete(firstDirector);
        directorRepository.delete(secondDirector);
    }

    @Test
    void shouldPatchDirector() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dob = LocalDate.of(1980, 8, 8);
        Double height = 188.8;
        String nationality = "American";

        String newFirstName = "Jane";
        String newLastName = "Wu";
        LocalDate newDob = LocalDate.of(1990, 9, 9);

        // Act
        Director director = directorRepository.save(
                new Director(
                        firstName,
                        lastName,
                        dob,
                        height,
                        nationality
                )
        );

        Integer createdDirectorId = director.getDirectorId();

        Director patchedDirector = sut.patch(
                director.getDirectorId(),
                newFirstName,
                newLastName,
                null,
                null,
                null
        );

        Integer patchedDirectorId = patchedDirector.getDirectorId();

        // Assert
        assertNotNull(sut.getById(patchedDirectorId));
        assertEquals(createdDirectorId, patchedDirectorId);
        assertEquals(newFirstName, sut.getById(patchedDirectorId).getFirstName());
        assertEquals(newLastName, sut.getById(patchedDirectorId).getLastName());
        assertEquals(dob, sut.getById(patchedDirectorId).getDateOfBirth());
        assertNotNull(sut.getById(patchedDirectorId).getNationality());
        assertNotNull(sut.getById(patchedDirectorId).getHeight());

        // Act
        Director patchedWithNullDirector = sut.patch(
                patchedDirectorId,
                null,
                null,
                null,
                null,
                null
        );

        Integer patchedWithNullDirectorId = patchedWithNullDirector.getDirectorId();

        // Assert
        assertNotNull(sut.getById(patchedWithNullDirectorId));
        assertEquals(patchedDirectorId, patchedWithNullDirectorId);
        assertEquals(newFirstName, sut.getById(patchedWithNullDirectorId).getFirstName());
        assertEquals(newLastName, sut.getById(patchedWithNullDirectorId).getLastName());
        assertEquals(dob, sut.getById(patchedWithNullDirectorId).getDateOfBirth());
        assertNotNull(sut.getById(patchedWithNullDirectorId).getNationality());
        assertNotNull(sut.getById(patchedWithNullDirectorId).getHeight());

        directorRepository.delete(patchedWithNullDirector);

        assertNull(sut.getById(patchedWithNullDirectorId));
        assertNull(sut.getById(patchedDirectorId));
        assertNull(sut.getById(createdDirectorId));
    }

    @Test
    void shouldNotPatchDirectorThatDoesNotExist() {
        // Act
        Executable executable = () -> sut.patch(
                999,
                null,
                "Doe",
                null,
                "Mexican",
                null
        );

        // Assert
        assertThrows(
                NullPointerException.class,
                executable
        );
    }

    @Test
    void onlyCreatorShouldBeAbleToModify() {
        // Arrange
        String johnDoeUser = "john_doe";
        String samUser = "sam";
        String trueUser = "oleksii";
        String emptyUser = null;

        // Act
        Boolean johnDoeResult = sut.canUserModify(johnDoeUser, 1);
        Boolean trueResult = sut.canUserModify(trueUser, 1);
        Boolean samResult = sut.canUserModify(samUser, 1);
        Boolean emptyUserResult = sut.canUserModify(emptyUser, 1);

        // Assert
        assertFalse(johnDoeResult);
        assertTrue(trueResult);
        assertNotNull(emptyUserResult);
        assertFalse(emptyUserResult);
        assertFalse(samResult);
    }


}
