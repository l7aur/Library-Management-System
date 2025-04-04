package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.repositories.AppUserRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AppUserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepo repo;

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
        mockMvc.perform(get("/app_users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(10))
                .andExpect(jsonPath("$[*].username",
                        Matchers.containsInAnyOrder(
                                "user1", "user2", "user3", "user4", "user5",
                                "user6", "user7", "user8", "user9", "user10"
                        )))
                .andExpect(jsonPath("$[*].password",
                        Matchers.containsInAnyOrder(
                                "password1", "password2", "password3", "password4", "password5",
                                "password6", "password7", "password8", "password9", "password10"
                        )))
                .andExpect(jsonPath("$[*].role",
                        Matchers.containsInAnyOrder(
                                "ADMIN", "ADMIN", "ADMIN", "ADMIN", "EMPLOYEE",
                                "EMPLOYEE", "EMPLOYEE", "CUSTOMER", "CUSTOMER", "CUSTOMER"
                        )))
                .andExpect(jsonPath("$[*].firstName",
                        Matchers.containsInAnyOrder(
                                "John", "Jane", "Alice", "Bob", "Charlie",
                                "Emily", "David", "Sophia", "Michael", "Olivia"
                        )))
                .andExpect(jsonPath("$[*].lastName",
                        Matchers.containsInAnyOrder(
                                "Doe", "Smith", "Johnson", "Brown", "White",
                                "Green", "Black", "Gray", "Blue", "Yellow"
                        )));
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
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "app_user_seed0.json");
            List<AppUser> users = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});

            // Hash passwords before saving
            //users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
            users.forEach(user -> user.setId(null)); // Ensure entities are new before saving
            repo.saveAll(users);
            System.out.println("Database successfully seeded with user data.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load seed data JSON file.", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while seeding the database.", e);
        }
    }

}
