package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.repository.DirectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final UserService userService;

    public DirectorServiceImpl(
            DirectorRepository directorRepository,
            UserService userService
    ) {
        this.directorRepository = directorRepository;
        this.userService = userService;
    }

    @Override
    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    @Override
    public Director getById(Integer id) {
        return directorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Movie> getMoviesForDirectorById(Integer id) {

        Director director = directorRepository.findById(id).orElse(null);

        if (director == null) {
            return null;
        }

        return director.getMovies();
    }

    @CacheEvict(value = "movie-search", allEntries = true)
    @Override
    public void delete(Integer id) {
        directorRepository.deleteById(id);
    }

    @CacheEvict(value = "movie-search", allEntries = true)
    @Override
    public Director add(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String nationality,
            Double height,
            User creator
    ) {
        Director director = new Director();
        director.setFirstName(firstName);
        director.setLastName(lastName);
        director.setDateOfBirth(dateOfBirth);
        director.setCreator(creator);

        if (nationality != null) {
            director.setNationality(nationality);
        }
        if (height != null) {
            director.setHeight(height);
        }

        return directorRepository.save(director);
    }

    @CacheEvict(value = "movie-search", allEntries = true)
    @Override
    public Director patch(
            Integer id,
            String firstName,
            String lastName,
            LocalDate birthdate,
            String nationality,
            Double height
    ) {
        Director director = directorRepository.findById(id).orElse(null);

        if (director == null) {
            return null;
        }

        if (firstName != null) {
            director.setFirstName(firstName);
        }
        if (lastName != null) {
            director.setLastName(lastName);
        }
        if (birthdate != null) {
            director.setDateOfBirth(birthdate);
        }
        if (nationality != null) {
            director.setNationality(nationality);
        }
        if (height != null) {
            director.setHeight(height);
        }

        directorRepository.save(director);

        return director;
    }

    @Override
    public Boolean canUserModify(String username, Integer id) {
        User creator = userService.findByUsername(username);

        return creator != null && directorRepository.existsByCreatorAndDirectorId(creator, id);
    }

    @Override
    public Boolean alreadyExists(String firstname, String lastname, LocalDate dateOfBirth) {
        Boolean doesExist = directorRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                firstname,
                lastname,
                dateOfBirth
        );

        return doesExist;
    }

    @Transactional
    @Async
    @CacheEvict(value = "movie-search", allEntries = true)
    @Override
    public void handleImport(InputStream inputStream, String creator) {
        User user = userService.findByUsername(creator);

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            if (split.length < 3) {
                continue;
            }
            String firstName = split[0];
            String lastName = split[1];
            LocalDate birthdate;

            try {
                birthdate = LocalDate.parse(split[2]);
            } catch (DateTimeParseException e) {
                continue;
            }

            String nationality = (split.length > 3 && !split[3].trim().isEmpty()) ? split[3] : null;

            Double height = null;

            if (split.length > 4 && !split[4].trim().isEmpty()) {
                try {
                    height = Double.valueOf(split[4]);
                } catch (NumberFormatException e) {
                    height = null;
                }
            }
            add(firstName, lastName, birthdate, nationality, height, user);

            try {
                TimeUnit.MILLISECONDS.sleep(
                        ThreadLocalRandom.current().nextInt(2000, 3000)
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
