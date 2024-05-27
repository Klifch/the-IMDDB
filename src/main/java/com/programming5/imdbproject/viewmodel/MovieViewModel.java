package com.programming5.imdbproject.viewmodel;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.MovieStatus;
import com.programming5.imdbproject.domain.Studio;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class MovieViewModel {
    private Integer movieId;
    private String movieName;
    private String movieQuote;
    private LocalDate releaseDate;
    private MovieStatus status;
    private Double budget;
    private Double box_office;
    private Double rating;
    private String description;
    private Director director;
    private Studio studio;

    public MovieViewModel() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieQuote() {
        return movieQuote;
    }

    public void setMovieQuote(String movieQuote) {
        this.movieQuote = movieQuote;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        if (budget != null) {
            DecimalFormat df = new DecimalFormat("#.0");
            this.budget = Double.parseDouble(df.format(budget / 1000000.0));
        } else {
            this.budget = null;
        }
    }

    public Double getBox_office() {
        return box_office;
    }

    public void setBox_office(Double box_office) {
        if (box_office != null) {
            DecimalFormat df = new DecimalFormat("#.0");
            this.box_office = Double.parseDouble(df.format(box_office / 1000000.0));
        } else {
            this.box_office = null;
        }
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
