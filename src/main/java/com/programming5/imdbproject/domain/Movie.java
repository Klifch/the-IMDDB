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
}
