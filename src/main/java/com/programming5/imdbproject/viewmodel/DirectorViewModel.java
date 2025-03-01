package com.programming5.imdbproject.viewmodel;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;

import java.time.LocalDate;
import java.util.List;

public class DirectorViewModel {

    private Integer directorId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    private Double height;
    private List<Movie> movies;
    private boolean canModify;

    public DirectorViewModel() {
    }

    public DirectorViewModel(
            Integer directorId,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String nationality,
            Double height,
            List<Movie> movies,
            boolean canModify
    ) {
        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.height = height;
        this.movies = movies;
        this.canModify = canModify;
    }

    public static DirectorViewModel from(Director director, Boolean canModify) {
         return new DirectorViewModel(
                 director.getDirectorId(),
                 director.getFirstName(),
                 director.getLastName(),
                 director.getDateOfBirth(),
                 director.getNationality(),
                 director.getHeight(),
                 director.getMovies(),
                 canModify
        );
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }
}
