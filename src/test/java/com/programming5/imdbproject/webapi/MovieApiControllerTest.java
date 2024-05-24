package com.programming5.imdbproject.webapi;

import com.programming5.imdbproject.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@AutoConfigureMockMvc
public class MovieApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MovieRepository movieRepository;

//    @Autowired
//    DirectorRepository directorRepository;
//
//    @Autowired
//    StudioRepository studioRepository;
//
//    @BeforeEach
//    void setUpBefore() throws Exception {
//        Movie newMovie1 =  new Movie();
//        Movie newMovie2 =  new Movie();
//
//        Director director1 = new Director();
//        Director director2 = new Director();
//
//        Studio studio1 = new Studio();
//        Studio studio2 = new Studio();
//
//        director1.setDirectorId(999);
//        director1.setFirstName("John");
//        director1.setLastName("Doe");
//        director1.setDateOfBirth(LocalDate.of(1990, 1, 1));
//
//        director2.setDirectorId(888);
//        director2.setFirstName("Jane");
//        director2.setLastName("Doe");
//        director2.setDateOfBirth(LocalDate.of(1990, 1, 1));
//
//        directorRepository.save(director1);
//        directorRepository.save(director2);
//
////        Director director = directorRepository.findById(999).orElse(null);
////        System.out.println(director.getDirectorId());
//
//        studio1.setStudioId(999);
//        studio1.setName("Happy Studio");
//        studio1.setLocation("Happy avenue 1");
//
//        studio2.setStudioId(888);
//        studio2.setName("Sad studio");
//        studio2.setLocation("Sad avenue 2");
//
//        studioRepository.save(studio1);
//        studioRepository.save(studio2);
//
//        newMovie1.setMovieId(999);
//        newMovie1.setMovieName("Movie name 1");
//        newMovie1.setMovieQuote("Always watch movie 1 first!");
//        newMovie1.setReleaseDate(LocalDate.of(1980, 8, 8));
//        newMovie1.setStatus(MovieStatus.RELEASED);
//        newMovie1.setBox_office(1000D);
//        newMovie1.setBudget(1000D);
//        newMovie1.setDirector(directorRepository.findById(999).orElse(null));
//        newMovie1.setStudio(studioRepository.findById(999).orElse(null));
//
//        newMovie2.setMovieId(888);
//        newMovie2.setMovieName("Movie name 2");
//        newMovie2.setMovieQuote("Never follow movie 1 suggestions!");
//        newMovie2.setReleaseDate(LocalDate.of(1980, 8, 8));
//        newMovie2.setStatus(MovieStatus.RELEASED);
//        newMovie2.setBox_office(999999D);
//        newMovie2.setBudget(9999D);
//        newMovie2.setDirector(directorRepository.findById(888).orElse(null));
//        newMovie2.setStudio(studioRepository.findById(888).orElse(null));
//
//        movieRepository.save(newMovie1);
//        movieRepository.save(newMovie2);
//    }
//
//    @AfterEach
//    void tearDownAfter() throws Exception {
//        movieRepository.deleteById(999);
//        movieRepository.deleteById(888);
//
//        directorRepository.deleteById(999);
//        directorRepository.deleteById(888);
//
//        studioRepository.deleteById(999);
//        studioRepository.deleteById(888);
//
//    }

    @Test
    @WithUserDetails(value = "oleksii")
    void shouldSearchMovies() throws Exception {
        // Thanks, Raoul! After two years I finally see first use case for var!
        // Arrange
        var request = get("/api/movies")
                .param("searchTerm", "The");

        //Act
        var result = mockMvc.perform(request);

        //Assert
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("The Dark Knight"))
                .andExpect(jsonPath("$[0].name",
                        equalTo("The Dark Knight")))
                .andExpect(jsonPath("$[0].id",
                        equalTo(1)))
                .andExpect(jsonPath("$[*].name",
                        hasItem("The Dark Knight")))
                .andExpect(jsonPath("$[1].name")
                        .value("The Shape of Water"))
                .andExpect(jsonPath("$[1].name",
                        equalTo("The Shape of Water")))
                .andExpect(jsonPath("$[1].id",
                        equalTo(11)))
                .andExpect(jsonPath("$[*].name",
                        hasItem("The Shape of Water")))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldNotAllowToSearch() throws Exception {
        // Arrange
        var request = get("/api/movies")
                .param("searchTerm", "The").with(anonymous());

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
