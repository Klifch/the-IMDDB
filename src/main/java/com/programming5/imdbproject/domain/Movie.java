package com.programming5.imdbproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"director_id", "studio_id"})
        }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "movie_quote", nullable = false)
    private String movieQuote;

    @Column(name = "release", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "box_office")
    private Double box_office;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description")
    private String description;

    // Relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToOne(optional = false)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    public Movie() {
    }

    public Movie(
            String movieName,
            String movieQuote,
            LocalDate releaseDate,
            MovieStatus status,
            Director director,
            Studio studio
    ) {
        this.movieName = movieName;
        this.movieQuote = movieQuote;
        this.releaseDate = releaseDate;
        this.status = status;
        this.director = director;
        this.studio = studio;
    }

    public Movie(
            String movieName,
            String movieQuote,
            LocalDate releaseDate,
            MovieStatus status,
            Double budget,
            Double box_office,
            Double rating,
            String description,
            Director director,
            Studio studio
    ) {
        this.movieName = movieName;
        this.movieQuote = movieQuote;
        this.releaseDate = releaseDate;
        this.status = status;
        this.budget = budget;
        this.box_office = box_office;
        this.rating = rating;
        this.description = description;
        this.director = director;
        this.studio = studio;
    }

    // Getters and setters

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

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getBox_office() {
        return box_office;
    }

    public void setBox_office(Double box_office) {
        this.box_office = box_office;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


}
