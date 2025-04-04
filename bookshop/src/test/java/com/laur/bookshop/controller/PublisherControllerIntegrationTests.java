package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.PublisherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

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
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "publisher_seed.json");
            List<Publisher> publishers = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});

            // Hash passwords before saving
            //users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
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
