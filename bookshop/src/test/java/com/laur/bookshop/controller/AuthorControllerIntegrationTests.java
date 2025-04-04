package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.repositories.AuthorRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void setUp() throws Exception {
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

    }

    @Test
    public void testAdd_InvalidPayload() throws Exception {

    }

    @Test
    public void deleteOne_ValidPayload() throws Exception {

    }

    @Test
    public void deleteOne_InvalidPayload() throws Exception {

    }

    @Test
    public void deleteMany_ValidPayload() throws Exception {

    }

    @Test
    public void deleteMany_InvalidPayload() throws Exception {

    }

    @Test
    public void update_ValidPayload() throws Exception {

    }

    @Test
    public void update_InvalidPayload() throws Exception {

    }

    private void seedDatabase() {
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
