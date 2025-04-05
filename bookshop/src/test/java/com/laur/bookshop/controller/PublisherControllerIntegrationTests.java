package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.PublisherRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PublisherControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublisherRepo repo;

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
        mockMvc.perform(get("/publishers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(10))
                .andExpect(jsonPath("$[*].name",
                        Matchers.containsInAnyOrder(
                                "Penguin Random House", "HarperCollins", "Simon & Schuster", "Hachette Livre", "Macmillan Publishers",
                                "Scholastic", "Pearson Education", "Oxford University Press", "Cambridge University Press", "Wiley"
                        )))
                .andExpect(jsonPath("$[*].location",
                        Matchers.containsInAnyOrder(
                                "New York, USA", "London, UK", "New York, USA", "Paris, France", "London, UK",
                                "New York, USA", "London, UK", "Oxford, UK", "Cambridge, UK", "Hoboken, USA"
                        )))
                .andExpect(jsonPath("$[*].foundingYear",
                        Matchers.containsInAnyOrder(
                                1927, 1817, 1924, 1826, 1843,
                                1920, 1844, 1586, 1534, 1807
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

    private void seedDatabase() throws Exception {
        try {
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "publisher_seed0.json");
            List<Publisher> publishers = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
            publishers.forEach(publisher -> publisher.setId(null));

            repo.saveAll(publishers);
            System.out.println("Database successfully seeded with publisher data.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load seed data JSON file.", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while seeding the database.", e);
        }
    }
}
