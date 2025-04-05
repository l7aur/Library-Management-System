package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.config.dto.AuthorDTO;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.repositories.AuthorRepo;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthorControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepo repo;

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        repo.deleteAll();
        repo.flush();
        seedDatabase();
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/authors/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(10))
                .andExpect(jsonPath("$[*].firstName",
                        Matchers.containsInAnyOrder(
                                "John", "Jane", "Alex", "Emily", "Michael",
                                "Sophia", "David", "Olivia", "Ethan", "Charlotte"
                        )))
                .andExpect(jsonPath("$[*].lastName",
                        Matchers.containsInAnyOrder(
                                "Doe", "Smith", "Brown", "Davis", "Wilson",
                                "Martinez", "Garcia", "Lee", "Nguyen", "Chen"
                        )))
                .andExpect(jsonPath("$[*].alias",
                        Matchers.containsInAnyOrder(
                                "J.D.", "J.S.", "A.B.", "E.D.", "M.W.",
                                "S.M.", "D.G.", "O.L.", "E.N.", "C.C."
                        )))
                .andExpect(jsonPath("$[*].nationality",
                        Matchers.containsInAnyOrder(
                                "American", "British", "Canadian", "Australian", "Spanish",
                                "Mexican", "Vietnamese", "Korean", "Chinese", "Irish"
                        )))
                .andExpect(jsonPath("$[*].books",
                        Matchers.everyItem(Matchers.is(Collections.emptyList()))));
    }

    @Test
    public void testAdd_ValidPayload() throws Exception {
        AuthorDTO dto = new AuthorDTO();
        dto.setFirstName("Johnny");
        dto.setLastName("Smith");
        dto.setAlias("David");
        dto.setNationality("Canada");
        dto.setBooks(Collections.emptyList());

        mockMvc.perform(post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.alias").value(dto.getAlias()))
                .andExpect(jsonPath("$.nationality").value(dto.getNationality()))
                .andExpect(jsonPath("$.books").isEmpty());
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload1() throws Exception {
        Author existingAuthor = new Author();
        existingAuthor.setFirstName("Mihai");
        existingAuthor.setLastName("Eminescu");
        existingAuthor.setAlias("Luceafarul poeziei romanesti");
        existingAuthor.setBooks(Collections.emptyList());
        existingAuthor.setNationality("romanian");
        repo.save(existingAuthor);

        AuthorDTO dto = existingAuthor.toDTO();
        mockMvc.perform(post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value(dto.getFirstName() + " " + dto.getLastName() + " already exists!"));
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload2() throws Exception {
        AuthorDTO dto = new AuthorDTO();
        dto.setFirstName("Mihai");
        dto.setLastName("Eminescu");
        dto.setAlias("Luceafarul poeziei romanesti");
        dto.setBooks(List.of("78901234-7890-1234-5b12-678901234567"));
        dto.setNationality("romanian");

        mockMvc.perform(post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("78901234-7890-1234-5b12-678901234567 not found!"));
    }

    @Test
    @Transactional
    public void testDeleteOne_ValidIds() throws Exception {
        List<Author> authors = repo.findAll();
        Author author1 = authors.getFirst();
        Author author2 = authors.getLast();

        List<String> idsToDelete = Arrays.asList(author1.getId().toString(), author2.getId().toString());

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/authors/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Authors deleted successfully!"));

        assertFalse(repo.existsById(author1.getId()));
        assertFalse(repo.existsById(author2.getId()));
    }

    @Test
    @Transactional
    public void testDeleteOne_InvalidIds1() throws Exception {
        List<Author> authors = repo.findAll();
        Author author1 = authors.getFirst();
        Author author2 = authors.getLast();

        List<String> idsToDelete = List.of("00000000-0000-0000-0000-000000000000");

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/authors/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Authors deleted successfully!"));

        assertTrue(repo.existsById(author1.getId()));
        assertTrue(repo.existsById(author2.getId()));
    }

    @Test
    @Transactional
    public void testDeleteOne_InvalidIds2() throws Exception {
        List<Author> authors = repo.findAll();
        Author author1 = authors.getFirst();
        Author author2 = authors.getLast();

        List<String> idsToDelete = Collections.emptyList();

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/authors/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("No IDs provided!"));

        assertTrue(repo.existsById(author1.getId()));
        assertTrue(repo.existsById(author2.getId()));
    }

    @Test
    public void testUpdate_ValidPayload() throws Exception {
        List<Author> authors = repo.findAll();

        Author updatedAuthor = new Author();
        updatedAuthor.setId(authors.getFirst().getId());
        updatedAuthor.setFirstName("Johnny");
        updatedAuthor.setLastName("Smith");
        updatedAuthor.setAlias("alias.x");
        updatedAuthor.setBooks(Collections.emptyList());
        updatedAuthor.setNationality("romanian");

        mockMvc.perform(put("/authors/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedAuthor.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedAuthor.getLastName()))
                .andExpect(jsonPath("$.books").isEmpty())
                .andExpect(jsonPath("$.nationality").value(updatedAuthor.getNationality()))
                .andExpect(jsonPath("$.alias").value(updatedAuthor.getAlias()));

        assertTrue(repo.existsById(updatedAuthor.getId()));
    }

    @Test
    public void testUpdate_InvalidPayload() throws Exception {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(UUID.randomUUID());
        updatedAuthor.setFirstName("Johnny");
        updatedAuthor.setLastName("Smith");
        updatedAuthor.setAlias("alias.x");
        updatedAuthor.setBooks(Collections.emptyList());
        updatedAuthor.setNationality("romanian");

        mockMvc.perform(put("/authors/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedAuthor)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(updatedAuthor.getFirstName() + " " + updatedAuthor.getLastName() + " not found!"));

        assertFalse(repo.existsById(updatedAuthor.getId()));
    }

    private void seedDatabase() throws RuntimeException {
        try {
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "author_seed0.json");
            List<Author> authors = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
            authors.forEach(author -> author.setId(null));

            repo.saveAll(authors);
            System.out.println("Database successfully seeded with author data.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load seed data JSON file.", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while seeding the database.", e);
        }
    }
}
