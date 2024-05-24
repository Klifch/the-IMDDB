package com.programming5.imdbproject.webapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.programming5.imdbproject.domain.Role;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.dto.AddDirectorDto;
import com.programming5.imdbproject.dto.PatchDirectorDto;
import com.programming5.imdbproject.repository.DirectorRepository;
import com.programming5.imdbproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@AutoConfigureMockMvc
public class DirectorApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private UserRepository userRepository;

    /*
        Authenticated user with Regular authority
     */
    @Test
    @WithUserDetails(value = "oleksii")
    void shouldGetDirectorMovies() throws Exception {
        // Arrange
        var request = get("/api/directors/{id}/movies", 2);

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Pulp Fiction"))
                .andExpect(jsonPath("$[0].name",
                        equalTo("Pulp Fiction")))
                .andExpect(jsonPath("$[*].name",
                        hasItem("Pulp Fiction")))
                .andExpect(jsonPath("$[0].id")
                        .value(2))
                .andExpect(jsonPath("$[0].id",
                        equalTo(2)))
                .andExpect(jsonPath("$[*].id",
                        hasItem(2)))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldNotGetDirectorMoviesIfUnauthenticated() throws Exception {
        // Arrange
        var request = get("/api/directors/{id}/movies", 2).with(anonymous());

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /*
        User with Editor authority
     */
    @Test
    @WithUserDetails(value = "sam")
    void shouldAddDirectorIfAuthorizedAsEditor() throws Exception {
        // Arrange
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        var request = post("/api/directors")
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act
        var response = mockMvc.perform(request);

        // Assert
        var responseJson = response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(dto.firstName()))
                .andExpect(jsonPath("$.lastName").value(dto.lastName()))
                .andExpect(jsonPath("$.dateOfBirth")
                        .value(
                                dto.dateOfBirth()
                                        .format(DateTimeFormatter
                                                .ofPattern("yyyy-MM-dd")
                                        )
                        ))
                .andExpect(jsonPath("$.nationality").value(dto.nationality()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdDirectorId = JsonPath.read(responseJson, "$.id");

        mockMvc.perform(get("/directors/{id}", createdDirectorId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "sam"))
                .andExpect(model().attribute("director",
                        hasProperty("directorId", equalTo(createdDirectorId))));

        directorRepository.deleteById(Integer.valueOf(createdDirectorId.toString()));

        mockMvc.perform(get("/directors/{id}", createdDirectorId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /*
        User with Admin authority
     */
    @Test
    @WithUserDetails(value = "jo3")
    void shouldAddDirectorIfAuthorizedAsAdmin() throws Exception {
        // Arrange
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        var request = post("/api/directors")
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act
        var response = mockMvc.perform(request);

        // Assert
        var responseJson = response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(dto.firstName()))
                .andExpect(jsonPath("$.lastName").value(dto.lastName()))
                .andExpect(jsonPath("$.dateOfBirth")
                        .value(
                                dto.dateOfBirth()
                                        .format(DateTimeFormatter
                                                .ofPattern("yyyy-MM-dd")
                                        )
                        ))
                .andExpect(jsonPath("$.nationality").value(dto.nationality()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdDirectorId = JsonPath.read(responseJson, "$.id");

        mockMvc.perform(get("/directors/{id}", createdDirectorId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "jo3"))
                .andExpect(model().attribute("director",
                        hasProperty("directorId", equalTo(createdDirectorId))));

        directorRepository.deleteById(Integer.valueOf(createdDirectorId.toString()));

        mockMvc.perform(get("/directors/{id}", createdDirectorId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /*
        Regular user without Editor authority
     */
    @Test
    @WithUserDetails(value = "oleksii")
    void shouldNotAddDirectorIfUnauthorized() throws Exception {
        // Arrange
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        var request = post("/api/directors")
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotAddDirectorIfUnauthenticated() throws Exception {
        // Arrange
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        var request = post("/api/directors")
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /*
        Should patch if user is Editor and creator for Director record
     */
    @Test
    @WithUserDetails(value = "sam")
    void shouldPatchDirectorIfEditorAndCreator() throws Exception {
        // Arrange
        Integer directorId = createDirectorAndGetId();

        var dto = new PatchDirectorDto(
                "Jane",
                null,
                null,
                "American",
                null
        );

        var request = patch("/api/directors/{id}", directorId)
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act
        var response = mockMvc.perform(request);

        // Assert
        var responseJson = response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(dto.firstName()))
                .andExpect(jsonPath("$.nationality").value(dto.nationality()))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var patchedId = JsonPath.read(responseJson, "$.id");

        directorRepository.deleteById(Integer.valueOf(patchedId.toString()));

        mockMvc.perform(get("/directors/{id}", patchedId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /*
        Should patch if user is admin and not author of Director record
     */
    @Test
    @WithUserDetails(value = "jo3")
    void shouldPatchIfAuthorizedAsAdmin() throws Exception {
        // Act (1)
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        // Assert (1)
        // Check if Director record created by another user
        mockMvc.perform(get("/directors/{id}", directorId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "john"))
                .andExpect(model().attribute("director",
                        hasProperty("directorId", equalTo(directorId))));

        // Arrange (2)
        var dto = new PatchDirectorDto(
                "Jane",
                null,
                null,
                "American",
                null
        );

        var request = patch("/api/directors/{id}", directorId)
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act (2)
        var response = mockMvc.perform(request);

        // Assert (2)
        var responseJson = response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(dto.firstName()))
                .andExpect(jsonPath("$.nationality").value(dto.nationality()))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var patchedId = JsonPath.read(responseJson, "$.id");

        directorRepository.deleteById(Integer.valueOf(patchedId.toString()));

        mockMvc.perform(get("/directors/{id}", patchedId))
                .andDo(print())
                .andExpect(status().isNotFound());

        userRepository.delete(userRepository.findUserByUsername("john"));

    }

    /*
        Should not patch if user is Editor but not creator of the Record
     */
    @Test
    @WithUserDetails(value = "sam")
    void shouldNotPatchIfEditorButNotCreator() throws Exception {
        // Act (1)
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        // Assert (1)
        // Check if Director record created by another user
        mockMvc.perform(get("/directors/{id}", directorId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "john"))
                .andExpect(model().attribute("director",
                        hasProperty("directorId", equalTo(directorId))));

        // Arrange (2)
        var dto = new PatchDirectorDto(
                "Jane",
                null,
                null,
                "American",
                null
        );

        var request = patch("/api/directors/{id}", directorId)
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act (2)
        var response = mockMvc.perform(request);

        // Assert (2)
        response
                .andDo(print())
                .andExpect(status().isForbidden());

        directorRepository.deleteById(directorId);

        mockMvc.perform(get("/directors/{id}", directorId))
                .andDo(print())
                .andExpect(status().isNotFound());

        userRepository.delete(userRepository.findUserByUsername("john"));
    }

    /*
        Should not patch if Regular user
     */
    @Test
    @WithUserDetails(value = "oleksii")
    void shouldNotPatchIfRegularUser() throws Exception {
        // Act (1)
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        // Assert (1)
        // Check if Director record created by another user
        mockMvc.perform(get("/directors/{id}", directorId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "john"))
                .andExpect(model().attribute("director",
                        hasProperty("directorId", equalTo(directorId))));

        // Arrange (2)
        var dto = new PatchDirectorDto(
                "Jane",
                null,
                null,
                "American",
                null
        );

        var request = patch("/api/directors/{id}", directorId)
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act (2)
        var response = mockMvc.perform(request);

        // Assert (2)
        response
                .andDo(print())
                .andExpect(status().isForbidden());

        directorRepository.deleteById(directorId);

        mockMvc.perform(get("/directors/{id}", directorId))
                .andDo(print())
                .andExpect(status().isNotFound());

        userRepository.delete(userRepository.findUserByUsername("john"));
    }

    /*
        Should not patch if Unauthenticated
     */
    @Test
    void shouldNotPatchIfUnAuthenticated() throws Exception {
        // Arrange
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        var dto = new PatchDirectorDto(
                "Jane",
                null,
                null,
                "American",
                null
        );

        var request = patch("/api/directors/{id}", directorId)
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));

        // Act (2)
        var response = mockMvc.perform(request);

        // Assert (2)
        response
                .andDo(print())
                .andExpect(status().isForbidden());

        directorRepository.deleteById(directorId);

        assertNull(directorRepository.findById(directorId).orElse(null));

        mockMvc.perform(get("/directors/{id}", directorId))
                .andDo(print())
                .andExpect(status().is(302)); // Moved Temporarily somehow considered Deprecated????

        userRepository.delete(userRepository.findUserByUsername("john"));
    }

    private Integer createDirectorAndGetId() throws Exception {
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );
        var request = post("/api/directors")
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));
        var response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return JsonPath.read(response, "$.id");
    }

    private Integer createDirectorAndGetIdAsUnknownEditor() throws Exception {
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        userRepository.save(new User("john", "123", true));

        var request = post("/api/directors")
                .with(SecurityMockMvcRequestPostProcessors.user("john").password("123").roles("EDITOR"))
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));
        var response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();


        return JsonPath.read(response, "$.id");
    }

}
