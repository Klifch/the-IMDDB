package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
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

    @Override
    public void delete(Integer id) {
        directorRepository.deleteById(id);
    }

    @Override
    public Director add(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String nationality,
            Double height
    ) {
        Director director = new Director();
        director.setFirstName(firstName);
        director.setLastName(lastName);
        director.setDateOfBirth(dateOfBirth);

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
    public Director patch(
            Integer id,
            String firstName,
            String lastName,
            LocalDate birthdate,
            String nationality,
            Double height
    ) {
        Director director = getById(id);

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


}
