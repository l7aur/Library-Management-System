package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.config.dto.PublisherDTO;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.PublisherRepo;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.*;

import static com.laur.bookshop.config.enums.AppMessages.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@WithMockUser
public class PublisherControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublisherRepo repo;

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    public void setUp()  {
        repo.deleteAll();
        repo.flush();
        seedDatabase();
    }

    @Test
    @Transactional
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
    @Transactional
    public void testAdd_ValidPayload() throws Exception {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("test");
        dto.setFoundingYear(1999);
        dto.setLocation("location");
        dto.setBookIds(Collections.emptyList());

        mockMvc.perform(post("/publishers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.foundingYear").value(dto.getFoundingYear()))
                .andExpect(jsonPath("$.location").value(dto.getLocation()))
                .andExpect(jsonPath("$.books").isEmpty());
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload1() throws Exception {
        Publisher existingPublisher = new Publisher();
        existingPublisher.setName("test");
        existingPublisher.setFoundingYear(1999);
        existingPublisher.setLocation("location");
        existingPublisher.setBooks(Collections.emptyList());
        repo.save(existingPublisher);

        PublisherDTO dto = existingPublisher.toDTO();
        mockMvc.perform(post("/publishers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString(PUBLISHER_DUPLICATE_MESSAGE)));
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload2() throws Exception {
        PublisherDTO dto = new PublisherDTO();
        dto.setName("test");
        dto.setFoundingYear(1999);
        dto.setLocation("location");
        dto.setBookIds(List.of("78901234-7890-1234-5b12-678901234567"));

        mockMvc.perform(post("/publishers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString(BOOK_NOT_FOUND_MESSAGE)));
    }

    @Test
    @Transactional
    public void testDeleteOne_ValidIds() throws Exception {
        List<Publisher> publishers = repo.findAll();
        Publisher publisher1 = publishers.getFirst();
        Publisher publisher2 = publishers.getLast();

        List<String> idsToDelete = Arrays.asList(publisher1.getId().toString(), publisher2.getId().toString());

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/publishers/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(containsString(PUBLISHER_DELETE_SUCCESS_MESSAGE)));

        assertFalse(repo.existsById(publisher1.getId()));
        assertFalse(repo.existsById(publisher2.getId()));
    }

    @Test
    @Transactional
    public void testDeleteOne_InvalidIds1() throws Exception {
        List<Publisher> publishers = repo.findAll();
        Publisher publisher1 = publishers.getFirst();
        Publisher publisher2 = publishers.getLast();

        List<String> idsToDelete = List.of("00000000-0000-0000-0000-000000000000");

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/publishers/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(containsString(PUBLISHER_DELETE_SUCCESS_MESSAGE)));

        assertTrue(repo.existsById(publisher1.getId()));
        assertTrue(repo.existsById(publisher2.getId()));
    }

    @Test
    @Transactional
    public void testDeleteOne_InvalidIds2() throws Exception {
        List<Publisher> publishers = repo.findAll();
        Publisher publisher1 = publishers.getFirst();
        Publisher publisher2 = publishers.getLast();

        List<String> idsToDelete = Collections.emptyList();

        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/publishers/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(containsString(PUBLISHER_DELETE_ERROR_MESSAGE)));

        assertTrue(repo.existsById(publisher1.getId()));
        assertTrue(repo.existsById(publisher2.getId()));
    }

    @Test
    public void testUpdate_ValidPayload() throws Exception {
        List<Publisher> publishers = repo.findAll();

        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setId(publishers.getFirst().getId());
        updatedPublisher.setName("test");
        updatedPublisher.setFoundingYear(1999);
        updatedPublisher.setLocation("location");
        updatedPublisher.setBooks(Collections.emptyList());

        mockMvc.perform(put("/publishers/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedPublisher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedPublisher.getName()))
                .andExpect(jsonPath("$.foundingYear").value(updatedPublisher.getFoundingYear()))
                .andExpect(jsonPath("$.books").isEmpty())
                .andExpect(jsonPath("$.location").value(updatedPublisher.getLocation()));

        assertTrue(repo.existsById(updatedPublisher.getId()));
    }

    @Test
    public void testUpdate_InvalidPayload() throws Exception {
        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setId(UUID.randomUUID());
        updatedPublisher.setName("test");
        updatedPublisher.setFoundingYear(1999);
        updatedPublisher.setLocation("location");
        updatedPublisher.setBooks(Collections.emptyList());

        mockMvc.perform(put("/publishers/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedPublisher.toDTO())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString(PUBLISHER_NOT_FOUND_MESSAGE)));

        assertFalse(repo.existsById(updatedPublisher.getId()));
    }

    private void seedDatabase() throws RuntimeException {
        try {
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "publisher_seed0.json");
            List<Publisher> publishers = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
            publishers.forEach(publisher -> publisher.setId(null));

            repo.saveAll(publishers);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_LOADING_JSON_DATA_MESSAGE, e);
        } catch (Exception e) {
            throw new RuntimeException(FAILED_SEEDING_DATABASE_MESSAGE, e);
        }
    }

}
