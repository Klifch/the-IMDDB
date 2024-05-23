package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.User;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository sut;

    @Test
    void entitiesPersistInDatabase() {
        // Act
        List<Director> directors = sut.findAll();

        // Assert
        assertFalse(directors.isEmpty());
        assertEquals(10, directors.size());
    }

    @Test
    void shouldRetrieveDirector() {
        // Act
        Director director = sut.findById(1).orElse(null);

        // Assert
        assertNotNull(director);
        assertEquals(
                "Christopher",
                director.getFirstName()
        );
    }

    @Test
    void shouldSaveAndDeleteDirector() {
        // Arrange
        Director directorOne = new Director(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                188.8,
                "American"
        );

        // Act
        sut.save(directorOne);
        Director savedDirector = sut.findById(11).orElse(null);

        // Assert
        assertNotNull(savedDirector, "Director should be present in the Database");
        assertEquals(
                "John",
                savedDirector.getFirstName()
        );

        sut.deleteById(11);

        assertNull(sut.findById(11).orElse(null));
    }

    @Test
    void shouldNotAllowDuplicateByFullNameAndAge() {
        // Arrange
        Director directorOne = new Director(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                188.8,
                "American"
                );

        Director directorTwo = new Director(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                198.8,
                "Mexican"
        );

        Director directorThree = new Director(
                "",
                "",
                null,
                198.8,
                "Mexican"
        );

        sut.save(directorOne);

        // Act
        Executable action1 = () -> {
            // this code block should throw an exception
            sut.save(directorTwo);
        };

        Executable action2 = () -> {
            // this code block should throw an exception
            sut.save(directorThree);
        };

        // Assert
        assertThrows(
                DataIntegrityViolationException.class,
                action1,
                "Directors should be unique by firstName, lastName and DOB"
        );

        assertThrows(
                DataIntegrityViolationException.class,
                action2,
                "Directors should should have a firstName, lastName and DOB"
        );

        sut.deleteById(directorOne.getDirectorId());
    }

    @Test
    void creatorLoadedLazy() {
        // Act
        Director director = sut.findById(1).orElse(null);

        Executable action = () -> {
            // this code block should throw an exception
            director.getCreator().getUsername();
        };

        // Assert
        assertNotNull(director, "Director should be present in the Database");
        assertNotNull(director.getCreator());
        assertTrue(Hibernate.isPropertyInitialized(director, "creator"));
        assertFalse(Hibernate.isInitialized(director.getCreator()));
        assertThrows(
                LazyInitializationException.class,
                action,
                "Shouldn't return name of uninitialized proxy"
        );
    }

}
