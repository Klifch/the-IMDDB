package com.programming5.imdbproject.webapi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
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
public class MovieApiControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    MovieRepository movieRepository;
//
//    @Autowired
//    DirectorRepository directorRepository;
//
//    @Autowired
//    StudioRepository studioRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        directorRepository.deleteAll();
//        studioRepository.deleteAll();
//        movieRepository.deleteAll();
//        userRepository.deleteAll();
//
//
//
//        Director director1 = directorRepository.save(new Director(
//                "Christopher",
//                "Nolan",
//                LocalDate.of(1970, 7, 30),
//                180.0,
//                "UK"
//        ));
//
//        Studio studio1 = studioRepository.save(new Studio(
//                "Warner Bros",
//                "Burbank, California"
//        ));
//
//        movieRepository.save(new Movie(
//                "The Dark Knight",
//                "Why so serious?",
//                LocalDate.of(2008, 7, 18),
//                MovieStatus.RELEASED,
//                director1,
//                studio1
//        ));
//
//        Director director2 = directorRepository.save(new Director(
//                "Guillermo",
//                "del Toro",
//                LocalDate.of(1964, 10, 9),
//                178.0,
//                "Mexico"
//        ));
//
//        movieRepository.save(new Movie(
//                "The Shape of Water",
//                "Unable to perceive the shape of You",
//                LocalDate.of(2017, 12, 1),
//                MovieStatus.RELEASED,
//                director2,
//                studio1
//        ));
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
                        equalTo(11))) // was 11 -> 2
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
