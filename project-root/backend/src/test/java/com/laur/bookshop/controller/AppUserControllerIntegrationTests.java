//package com.laur.bookshop.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jayway.jsonpath.JsonPath;
//import com.laur.bookshop.config.dto.AppUserDTO;
//import com.laur.bookshop.model.AppUser;
//import com.laur.bookshop.config.dto.LoginRequest;
//import com.laur.bookshop.repositories.AppUserRepo;
//import jakarta.transaction.Transactional;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.io.IOException;
//import java.util.*;
//
//import static com.laur.bookshop.config.enums.AppMessages.*;
//import static com.laur.bookshop.config.enums.Role.*;
//import static org.hamcrest.Matchers.containsString;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.properties")
//@WithMockUser
//public class AppUserControllerIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AppUserRepo repo;
//
//    private static final String FIXTURE_PATH = "src/test/resources/fixtures/";
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void setUp() {
//        repo.deleteAll();
//        repo.flush();
//        seedDatabase();
//    }
//
//    @Test
//    @Transactional
//    public void testFindAll() throws Exception {
//        List<String> users = List.of(
//                "user1", "user2", "user3", "user4", "user5",
//                "user6", "user7", "user8", "user9", "user10");
//        MvcResult result = mockMvc.perform(get("/app_users/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()")
//                        .value(10))
//                .andExpect(jsonPath("$[*].username",
//                        Matchers.oneOf(users)))
//                .andExpect(jsonPath("$[*].role",
//                        Matchers.containsInAnyOrder(
//                                "ADMIN", "ADMIN", "ADMIN", "ADMIN", "EMPLOYEE",
//                                "EMPLOYEE", "EMPLOYEE", "CUSTOMER", "CUSTOMER", "CUSTOMER"
//                        )))
//                .andExpect(jsonPath("$[*].firstName",
//                        Matchers.containsInAnyOrder(
//                                "John", "Jane", "Alice", "Bob", "Charlie",
//                                "Emily", "David", "Sophia", "Michael", "Olivia"
//                        )))
//                .andExpect(jsonPath("$[*].lastName",
//                        Matchers.containsInAnyOrder(
//                                "Doe", "Smith", "Johnson", "Brown", "White",
//                                "Green", "Black", "Gray", "Blue", "Yellow"
//                        )))
//                .andExpect(jsonPath("$[*].password").exists()).andReturn();
//        String responseContent = result.getResponse().getContentAsString();
//        List<String> actualPasswords = JsonPath.read(responseContent, "$[*].password");
//        List<String> clearPasswords = List.of("password1", "password2", "password3", "password4", "password5", "password6", "password7", "password8", "password9", "password10");
//        for(String expected : clearPasswords) {
//            boolean found = false;
//            for(String p : actualPasswords) {
//                if(passwordEncoder.matches(expected, p)) {
//                    found = true;
//                    break;
//                }
//            }
//            if(!found)
//                throw new AssertionError("Passwords don't match");
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testAdd_ValidPayload() throws Exception {
//        AppUserDTO dto = new AppUserDTO();
//        dto.setFirstName("John");
//        dto.setLastName("Smith");
//        dto.setUsername("john.smith.1");
//        dto.setPassword("Password!1");
//        dto.setRole("ADMIN");
//        MvcResult result = mockMvc.perform(post("/app_users/add")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(MAPPER.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(dto.getUsername()))
//                .andExpect(jsonPath("$.role").value(dto.getRole()))
//                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
//                .andExpect(jsonPath("$.password").exists()).andReturn();
//        String responseContent = result.getResponse().getContentAsString();
//        String actualPassword = JsonPath.read(responseContent, "$.password");
//        assertTrue(passwordEncoder.matches(dto.getPassword(), actualPassword));
//    }
//
//    @Test
//    @Transactional
//    public void testAdd_InvalidPayload1() throws Exception {
//        AppUserDTO dto = new AppUserDTO();
//        dto.setFirstName("John");
//        dto.setLastName("Smith");
//        dto.setUsername("john.smith.1");
//        dto.setPassword("Password!");
//        dto.setRole("ADMIN");
//
//        mockMvc.perform(post("/app_users/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", containsString(PASSWORD_VALIDATOR_ERROR_MESSAGE_DIGIT)));
//    }
//
//    @Test
//    @Transactional
//    public void testAdd_InvalidPayload2() throws Exception {
//        AppUserDTO dto = new AppUserDTO();
//        dto.setFirstName("John");
//        dto.setLastName("Smith");
//        dto.setUsername("john.smith.1");
//        dto.setPassword("Password!");
//        dto.setRole("ADMIN");
//        mockMvc.perform(post("/app_users/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value(containsString(PASSWORD_VALIDATOR_ERROR_MESSAGE_DIGIT)));
//    }
//
//    @Test
//    @Transactional
//    public void testAdd_InvalidPayload3() throws Exception {
//        AppUser existingUser = new AppUser();
//        existingUser.setUsername("john.smith.1");
//        existingUser.setFirstName("Existing");
//        existingUser.setLastName("User");
//        existingUser.setPassword("ExistingPassword1!");
//        existingUser.setRole(ADMIN);
//        repo.save(existingUser);
//        assertTrue(repo.existsById(existingUser.getId()));
//
//        AppUserDTO dto = existingUser.toDTO();
//        mockMvc.perform(post("/app_users/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(dto)))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value(containsString(USER_DUPLICATE_MESSAGE)));
//        assertTrue(repo.existsById(existingUser.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void testDelete_ValidIds() throws Exception {
//        List<AppUser> users = repo.findAll();
//        AppUser user1 = users.getFirst();
//        AppUser user2 = users.getLast();
//
//        List<String> idsToDelete = Arrays.asList(user1.getId().toString(), user2.getId().toString());
//
//        Map<String, List<String>> requestPayload = new HashMap<>();
//        requestPayload.put("ids", idsToDelete);
//
//        mockMvc.perform(delete("/app_users/delete")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(requestPayload)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(USER_DELETE_SUCCESS_MESSAGE));
//
//        assertFalse(repo.existsById(user1.getId()));
//        assertFalse(repo.existsById(user2.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void testDelete_InvalidIds1() throws Exception {
//        List<AppUser> users = repo.findAll();
//        AppUser user1 = users.getFirst();
//        AppUser user2 = users.getLast();
//
//        List<String> idsToDelete = List.of("00000000-0000-0000-0000-000000000000");
//
//        Map<String, List<String>> requestPayload = new HashMap<>();
//        requestPayload.put("ids", idsToDelete);
//
//        mockMvc.perform(delete("/app_users/delete")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(requestPayload)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(USER_DELETE_SUCCESS_MESSAGE));
//        assertTrue(repo.existsById(user1.getId()));
//        assertTrue(repo.existsById(user2.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void testDelete_InvalidIds2() throws Exception {
//        List<AppUser> users = repo.findAll();
//        AppUser user1 = users.getFirst();
//        AppUser user2 = users.getLast();
//
//        List<String> idsToDelete = Collections.emptyList();
//
//        Map<String, List<String>> requestPayload = new HashMap<>();
//        requestPayload.put("ids", idsToDelete);
//
//        mockMvc.perform(delete("/app_users/delete")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(requestPayload)))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(USER_DELETE_ERROR_MESSAGE));
//
//        assertTrue(repo.existsById(user1.getId()));
//        assertTrue(repo.existsById(user2.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void update_ValidPayload() throws Exception {
//        AppUser initialUser = repo.findAll().getFirst();
//
//        AppUser updatedUser = new AppUser();
//        updatedUser.setId(initialUser.getId());
//        updatedUser.setUsername("new.test.user1");
//        updatedUser.setFirstName("UpdatedTest");
//        updatedUser.setLastName("UpdatedUser1");
//        updatedUser.setPassword("newPassword123!");
//        updatedUser.setRole(CUSTOMER);
//
//        MvcResult result = mockMvc.perform(put("/app_users/edit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(updatedUser.toDTO())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
//                .andExpect(jsonPath("$.role").value(updatedUser.getRole().toString()))
//                .andExpect(jsonPath("$.password").exists()).andReturn();
//        String responseContent = result.getResponse().getContentAsString();
//        String actualPassword = JsonPath.read(responseContent, "$.password");
//        assertTrue(passwordEncoder.matches(updatedUser.getPassword(), actualPassword));
//    }
//
//    @Test
//    @Transactional
//    public void update_InvalidPayload1() throws Exception {
//        AppUser initialUser = repo.findAll().getFirst();
//
//        AppUser updatedUser = new AppUser();
//        updatedUser.setId(UUID.randomUUID());
//        updatedUser.setUsername("new.test.user1");
//        updatedUser.setFirstName("UpdatedTest");
//        updatedUser.setLastName("UpdatedUser1");
//        updatedUser.setPassword("newPassword123!");
//        updatedUser.setRole(CUSTOMER);
//
//        mockMvc.perform(put("/app_users/edit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(updatedUser.toDTO())))
//                .andExpect(status().isNotFound())
//                        .andExpect(jsonPath("$.message").value(containsString(USER_NOT_FOUND_MESSAGE)));
//        assertTrue(repo.existsById(initialUser.getId()));
//        assertFalse(repo.existsById(updatedUser.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void update_InvalidPayload2() throws Exception {
//        AppUser updatedUser = repo.findAll().getFirst();
//        updatedUser.setUsername("new.test.user1");
//        updatedUser.setFirstName("UpdatedTest");
//        updatedUser.setLastName("UpdatedUser1");
//        updatedUser.setPassword("newPassword!");
//        updatedUser.setRole(CUSTOMER);
//
//        mockMvc.perform(put("/app_users/edit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(updatedUser.toDTO())))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value(containsString(PASSWORD_VALIDATOR_ERROR_MESSAGE_DIGIT)));
//    }
//
//    @Test
//    @Transactional
//    public void login_ValidPayload() throws Exception {
//        AppUser user = repo.findAll().getFirst();
//
//        LoginRequest lr = new LoginRequest("user1", "password1");
//        MvcResult result = mockMvc.perform(post("/app_users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(lr)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(user.getUsername()))
//                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
//                .andExpect(jsonPath("$.role").value(user.getRole().toString()))
//                .andExpect(jsonPath("$.password").exists()).andReturn();
//        String responseContent = result.getResponse().getContentAsString();
//        String actualPassword = JsonPath.read(responseContent, "$.password");
//        assertTrue(passwordEncoder.matches(lr.getPassword(), actualPassword));
//    }
//
//    @Test
//    @Transactional
//    public void login_InvalidPassword() throws Exception {
//        AppUser user = repo.findAll().getFirst();
//
//        LoginRequest lr = new LoginRequest(user.getUsername(), "a.wrong.password");
//
//        assertTrue(repo.existsById(user.getId()));
//        mockMvc.perform(post("/app_users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(lr)))
//                .andExpect(status().isUnauthorized())
//                        .andExpect(jsonPath("$.message").value(containsString(WRONG_PASSWORD_MESSAGE)));
//        assertTrue(repo.existsById(user.getId()));
//    }
//
//    @Test
//    @Transactional
//    public void login_InvalidUsername() throws Exception {
//        AppUser user = repo.findAll().getFirst();
//
//        LoginRequest lr = new LoginRequest("a.wrong.username", "a.wrong.password");
//
//        assertTrue(repo.existsById(user.getId()));
//        mockMvc.perform(post("/app_users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(MAPPER.writeValueAsString(lr)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(containsString(USER_NOT_FOUND_MESSAGE)));
//        assertTrue(repo.existsById(user.getId()));
//    }
//
//    private void seedDatabase() {
//        try {
//            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "app_user_seed0.json");
//            List<AppUser> users = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
//
//            users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
//            users.forEach(user -> user.setId(null));
//            repo.saveAll(users);
//        } catch (IOException e) {
//            throw new RuntimeException(FAILED_LOADING_JSON_DATA_MESSAGE, e);
//        } catch (Exception e) {
//            throw new RuntimeException(FAILED_SEEDING_DATABASE_MESSAGE, e);
//        }
//    }
//
//}
