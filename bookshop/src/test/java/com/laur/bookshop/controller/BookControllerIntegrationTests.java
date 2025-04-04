package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.repositories.BookRepo;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jmx.export.metadata.ManagedAttribute;
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
public class BookControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepo repo;

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
        mockMvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(10))
                .andExpect(jsonPath("$[*].isbn",
                        Matchers.containsInAnyOrder(
                                "978-3-16-148410-0", "978-0-12-345678-9", "978-1-234-56789-7", "978-9-87-654321-0", "978-4-56-789012-3",
                                "978-7-89-123456-0", "978-5-43-210987-6", "978-8-65-432109-3", "978-2-10-987654-3", "978-6-54-321098-7"
                        )))
                .andExpect(jsonPath("$[*].title",
                        Matchers.containsInAnyOrder(
                                "The Great Adventure", "Learning Java", "AI for Beginners", "Exploring Space", "History of Europe",
                                "Science Experiments", "The Art of Cooking", "Travel Destinations", "Programming in Python", "Gardening Tips"
                        )))
                .andExpect(jsonPath("$[*].authors",
                        Matchers.everyItem(Matchers.is(Collections.emptyList()))))
                .andExpect(jsonPath("$[*].publishYear",
                        Matchers.containsInAnyOrder(
                                2020, 2018, 2021, 2017, 2019,
                                2022, 2015, 2016, 2023, 2021
                        )))
                .andExpect(jsonPath("$[*].publisher",
                        Matchers.everyItem(Matchers.is(null))))
                .andExpect(jsonPath("$[*].price",
                        Matchers.containsInAnyOrder(
                                19.99, 29.99, 24.99, 34.99, 22.99,
                                16.99, 27.99, 32.99, 39.99, 14.99
                        )))
                .andExpect(jsonPath("$[*].stock",
                        Matchers.containsInAnyOrder(
                                100, 50, 75, 120, 90,
                                60, 80, 40, 70, 200
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

    private void seedDatabase() throws Exception {
        try {
            String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "book_seed0.json");
            List<Book> books = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
            books.forEach(book -> book.setId(null));

            repo.saveAll(books);
            System.out.println("Database successfully seeded with book data.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load seed data JSON file.", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while seeding the database.", e);
        }
    }
}
