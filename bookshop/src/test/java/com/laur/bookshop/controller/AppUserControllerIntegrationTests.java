package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.config.dto.AppUserDTO;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.LoginRequest;
import com.laur.bookshop.repositories.AppUserRepo;
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

import static com.laur.bookshop.config.enums.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void setUp() {
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
        AppUserDTO dto = new AppUserDTO();
        dto.setFirstName("John");
        dto.setLastName("Smith");
        dto.setUsername("john.smith.1");
        dto.setPassword("Password!1");
        dto.setRole("ADMIN");
        mockMvc.perform(post("/app_users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john.smith.1"))
                .andExpect(jsonPath("$.password").value("Password!1"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    public void testAdd_InvalidPayload1() throws Exception {
        AppUserDTO dto = new AppUserDTO();
        dto.setFirstName("John");
        dto.setLastName("Smith");
        dto.setUsername("john.smith.1");
        dto.setPassword("Password");
        dto.setRole("ADMIN");
        mockMvc.perform(post("/app_users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.password").value(Matchers.oneOf(
                                "Password must contain at least one special character",
                                "Password must contain at least one digit"
                        )
                ))
        ;
    }

    @Test
    public void testAdd_InvalidPayload2() throws Exception {
        AppUserDTO dto = new AppUserDTO();
        dto.setFirstName("John");
        dto.setLastName("Smith");
        dto.setUsername("john.smith.1");
        dto.setPassword("Password!");
        dto.setRole("ADMIN");
        mockMvc.perform(post("/app_users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.password").value("Password must contain at least one digit"));
    }

    @Transactional
    @Test
    public void testAdd_InvalidPayload3() throws Exception {
        AppUser existingUser = new AppUser();
        existingUser.setUsername("john.smith.1");
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setPassword("ExistingPassword1!");
        existingUser.setRole(ADMIN);
        repo.save(existingUser);

        AppUserDTO dto = existingUser.toDTO();
        mockMvc.perform(post("/app_users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("john.smith.1 already exists!"));
    }

    @Test
    @Transactional
    public void testDelete_ValidIds() throws Exception {
        List<AppUser> users = repo.findAll();
        AppUser user1 = users.getFirst();
        AppUser user2 = users.getLast();

        List<String> idsToDelete = Arrays.asList(user1.getId().toString(), user2.getId().toString());

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/app_users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("App User deleted successfully!"));

        assertFalse(repo.existsById(user1.getId()));
        assertFalse(repo.existsById(user2.getId()));
    }

    @Test
    @Transactional
    public void testDelete_InvalidIds1() throws Exception {
        List<AppUser> users = repo.findAll();
        AppUser user1 = users.getFirst();
        AppUser user2 = users.getLast();

        List<String> idsToDelete = List.of("00000000-0000-0000-0000-000000000000");

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/app_users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("App User deleted successfully!"));
        assertTrue(repo.existsById(user1.getId()));
        assertTrue(repo.existsById(user2.getId()));
    }

    @Test
    @Transactional
    public void testDelete_InvalidIds2() throws Exception {
        List<AppUser> users = repo.findAll();
        AppUser user1 = users.getFirst();
        AppUser user2 = users.getLast();

        List<String> idsToDelete = Collections.emptyList();

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/app_users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("No IDs provided!"));

        assertTrue(repo.existsById(user1.getId()));
        assertTrue(repo.existsById(user2.getId()));
    }

    @Test
    public void update_ValidPayload() throws Exception {
        AppUser initialUser = repo.findAll().getFirst();

        AppUser updatedUser = new AppUser();
        updatedUser.setId(initialUser.getId());
        updatedUser.setUsername("new.test.user1");
        updatedUser.setFirstName("UpdatedTest");
        updatedUser.setLastName("UpdatedUser1");
        updatedUser.setPassword("newPassword123!");
        updatedUser.setRole(CUSTOMER);

        mockMvc.perform(put("/app_users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.password").value(updatedUser.getPassword()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.role").value(updatedUser.getRole().toString()));

        assertTrue(repo.existsById(updatedUser.getId()));
    }

    @Test
    public void update_InvalidPayload() throws Exception {
        AppUser initialUser = repo.findAll().getFirst();

        AppUser updatedUser = new AppUser();
        updatedUser.setId(UUID.randomUUID());
        updatedUser.setUsername("new.test.user1");
        updatedUser.setFirstName("UpdatedTest");
        updatedUser.setLastName("UpdatedUser1");
        updatedUser.setPassword("newPassword123!");
        updatedUser.setRole(CUSTOMER);

        mockMvc.perform(put("/app_users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound())
                        .andExpect(content().string("new.test.user1 reference does not exist!"));
        assertTrue(repo.existsById(initialUser.getId()));
        assertFalse(repo.existsById(updatedUser.getId()));
    }

    @Test
    @Transactional
    public void login_ValidPayload() throws Exception {
        AppUser user = repo.findAll().getFirst();

        LoginRequest lr = new LoginRequest(user.getUsername(), user.getPassword());

        assertTrue(repo.existsById(user.getId()));
        mockMvc.perform(post("/app_users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(lr)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.role").value(user.getRole().toString()));
        assertTrue(repo.existsById(user.getId()));
    }

    @Test
    @Transactional
    public void login_InvalidPassword() throws Exception {
        AppUser user = repo.findAll().getFirst();

        LoginRequest lr = new LoginRequest(user.getUsername(), "a.wrong.password");

        assertTrue(repo.existsById(user.getId()));
        mockMvc.perform(post("/app_users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(lr)))
                .andExpect(status().isUnauthorized())
                        .andExpect(content().string("Wrong password for username: '" + lr.getUsername() + "'"));
        assertTrue(repo.existsById(user.getId()));
    }

    @Test
    @Transactional
    public void login_InvalidUsername() throws Exception {
        AppUser user = repo.findAll().getFirst();

        LoginRequest lr = new LoginRequest("a.wrong.username", "a.wrong.password");

        assertTrue(repo.existsById(user.getId()));
        mockMvc.perform(post("/app_users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(lr)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user found with username: '" + lr.getUsername() + "'"));
        assertTrue(repo.existsById(user.getId()));
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
