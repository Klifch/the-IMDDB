package com.programming5.imdbproject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "studios")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer studioId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "office_location", nullable = false)
    private String location;

    @Column(name = "total_employees")
    private Integer totalEmployees;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<Movie> movies;

    public Studio() {
    }

    public Studio(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Studio(String name, String location, Integer totalEmployees) {
        this.name = name;
        this.location = location;
        this.totalEmployees = totalEmployees;
    }

    // Getters and setters
    public Integer getStudioId() {
        return studioId;
    }

    public void setStudioId(Integer studioId) {
        this.studioId = studioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
