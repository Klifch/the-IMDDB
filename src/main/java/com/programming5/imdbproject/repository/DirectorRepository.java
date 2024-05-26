package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    // magick here
    Boolean existsByCreatorAndDirectorId(User creator, Integer directorId);

    Boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

}
