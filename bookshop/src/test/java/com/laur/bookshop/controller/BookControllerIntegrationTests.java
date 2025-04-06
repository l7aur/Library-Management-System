package com.laur.bookshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laur.bookshop.config.dto.BookDTO;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.PublisherRepo;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.laur.bookshop.config.enums.AppMessages.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepo repo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private AuthorRepo authorRepo;

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        repo.deleteAll();
        repo.flush();
        seedDatabases();
    }

    @Test
    @Transactional
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(1))
                .andExpect(jsonPath("$[*].isbn",
                        Matchers.containsInAnyOrder("978-0-12-345678-9")))
                .andExpect(jsonPath("$[*].title",
                        Matchers.containsInAnyOrder("Learning Java")))
                .andExpect(jsonPath("$[0].authors[0].firstName").value("Alex"))
                .andExpect(jsonPath("$[0].authors[0].lastName").value("Brown"))
                .andExpect(jsonPath("$[0].authors[0].alias").value("A.B."))
                .andExpect(jsonPath("$[0].authors[0].nationality").value("Canadian"))
                .andExpect(jsonPath("$[0].authors[1].firstName").value("Emily"))
                .andExpect(jsonPath("$[0].authors[1].lastName").value("Davis"))
                .andExpect(jsonPath("$[0].authors[1].alias").value("E.D."))
                .andExpect(jsonPath("$[0].authors[1].nationality").value("Australian"))
                .andExpect(jsonPath("$[*].publishYear").value(2018))
                .andExpect(jsonPath("$[0].publisher.name").value("Cambridge University Press"))
                .andExpect(jsonPath("$[0].publisher.location").value("Cambridge, UK"))
                .andExpect(jsonPath("$[0].publisher.foundingYear").value(1534))
                .andExpect(jsonPath("$[*].price").value(29.99))
                .andExpect(jsonPath("$[*].stock").value(50));
    }

    @Test
    @Transactional
    public void testAdd_ValidPayload() throws Exception {
        List<Publisher> publishers = publisherRepo.findAll();
        List<Author> authors = authorRepo.findAll();
        Book book = new Book();
        book.setIsbn("978-1-2345-6789-0");
        book.setTitle("New Title");
        book.setStock(100);
        book.setPrice(100.99);
        book.setPublishYear(2000);
        book.setAuthors(List.of(authors.getFirst(), authors.get(1), authors.get(2)));
        book.setPublisher(publishers.getFirst());

        BookDTO dto = book.toDTO();
        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.publishYear").value(book.getPublishYear()))
                .andExpect(jsonPath("$.publisher.name").value(book.getPublisher().getName()))
                .andExpect(jsonPath("$.publisher.location").value(book.getPublisher().getLocation()))
                .andExpect(jsonPath("$.publisher.foundingYear").value(book.getPublisher().getFoundingYear()))
                .andExpect(jsonPath("$.authors[0].firstName").value(book.getAuthors().getFirst().getFirstName()))
                .andExpect(jsonPath("$.authors[0].lastName").value(book.getAuthors().getFirst().getLastName()))
                .andExpect(jsonPath("$.authors[0].alias").value(book.getAuthors().getFirst().getAlias()))
                .andExpect(jsonPath("$.authors[0].nationality").value(book.getAuthors().getFirst().getNationality()))
                .andExpect(jsonPath("$.authors[1].firstName").value(book.getAuthors().get(1).getFirstName()))
                .andExpect(jsonPath("$.authors[1].lastName").value(book.getAuthors().get(1).getLastName()))
                .andExpect(jsonPath("$.authors[1].alias").value(book.getAuthors().get(1).getAlias()))
                .andExpect(jsonPath("$.authors[1].nationality").value(book.getAuthors().get(1).getNationality()));
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload1() throws Exception {
        List<Publisher> publishers = publisherRepo.findAll();
        Book book = new Book();
        book.setIsbn("978-1-2345-6789-0");
        book.setTitle("New Title");
        book.setStock(100);
        book.setPrice(100.99);
        book.setPublishYear(2000);
        book.setAuthors(Collections.emptyList());
        book.setPublisher(publishers.getFirst());

        BookDTO dto = book.toDTO();
        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.authorIds").value(MISSING_AUTHOR_IDS_MESSAGE));
    }

    @Test
    @Transactional
    public void testAdd_InvalidPayload2() throws Exception {
        Book book = new Book();
        book.setIsbn("978-1-2345-6789-0");
        book.setTitle("New Title");
        book.setStock(100);
        book.setPrice(100.99);
        book.setPublishYear(2000);
        book.setAuthors(authorRepo.findAll());
        Publisher fake = new Publisher();
        fake.setId(UUID.randomUUID());

        book.setPublisher(fake);

        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(book.toDTO())))
                .andExpect(status().isNotFound())
                .andExpect(content().string(PUBLISHER_NOT_FOUND_MESSAGE));
    }

    @Test
    @Transactional
    public void testDelete_ValidPayload() throws Exception {
        List<String> idsToDelete = repo.findAll().stream().map(x -> x.getId().toString()).toList();
        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/books/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string(BOOK_DELETE_SUCCESS_MESSAGE));
    }

    @Test
    @Transactional
    public void testDelete_InvalidPayload1() throws Exception {
        List<String> idsToDelete = List.of("00000000-0000-0000-0000-000000000000");
        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/books/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string(BOOK_DELETE_SUCCESS_MESSAGE));
    }

    @Test
    @Transactional
    public void testDelete_InvalidPayload2() throws Exception {
        List<String> idsToDelete = Collections.emptyList();
        Map<String, List<String>> requestPayload = new HashMap<>();
        requestPayload.put("ids", idsToDelete);

        mockMvc.perform(delete("/books/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(requestPayload)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(BOOK_DELETE_ERROR_MESSAGE));
    }

    @Test
    @Transactional
    public void testUpdate_ValidPayload() throws Exception {
        List<Book> books = repo.findAll();
        Book updatedBook = books.getFirst();
        updatedBook.setTitle("New Title");
        updatedBook.setStock(105);
        updatedBook.setPrice(13.99);
        updatedBook.setPublishYear(2001);

        mockMvc.perform(put("/books/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedBook.toDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.stock").value(updatedBook.getStock()))
                .andExpect(jsonPath("$.price").value(updatedBook.getPrice()))
                .andExpect(jsonPath("$.publishYear").value(updatedBook.getPublishYear()));

        assertTrue(repo.existsById(updatedBook.getId()));
    }

    @Test
    @Transactional
    public void testUpdate_InvalidPayload1() throws Exception {
        List<Book> books = repo.findAll();
        Book updatedBook = books.getFirst();
        updatedBook.setTitle("New Title");
        updatedBook.setStock(-105);
        updatedBook.setPrice(13.99);
        updatedBook.setPublishYear(3000);

        mockMvc.perform(put("/books/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updatedBook.toDTO())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.publishYear").value(PUBLISH_YEAR_NOT_VALID_MESSAGE))
                .andExpect(jsonPath("$.stock").value(STOCK_NOT_VALID_MESSAGE));

        assertTrue(repo.existsById(updatedBook.getId()));
    }

    private void seedDatabases() throws RuntimeException {
        try {
            Map<String, Publisher> publisherByName = seedPublishers();
            Map<String, Author> authorByName = seedAuthors();
            seedBooks(authorByName, publisherByName);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_LOADING_JSON_DATA_MESSAGE, e);
        } catch (Exception e) {
            throw new RuntimeException(FAILED_SEEDING_DATABASE_MESSAGE, e);
        }
    }

    private Map<String, Publisher> seedPublishers() throws IOException {
        String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "publisher_seed0.json");
        List<Publisher> publishers = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
        publishers.forEach(publisher -> publisher.setId(null));
        List<Publisher> savedPublishers = publisherRepo.saveAll(publishers);

        return savedPublishers.stream().collect(
                Collectors.toMap(Publisher::getName, Function.identity()));
    }

    private Map<String, Author> seedAuthors() throws IOException {
        String seedDataJSON = Util.loadFixture(FIXTURE_PATH, "author_seed0.json");
        List<Author> authors = MAPPER.readValue(seedDataJSON, new TypeReference<>() {});
        authors.forEach(author -> author.setId(null));
        List<Author> savedAuthors = authorRepo.saveAll(authors);

        return savedAuthors.stream().collect(
                Collectors.toMap(a -> a.getFirstName() + " " + a.getLastName(), Function.identity()));
    }

    private void seedBooks(Map<String, Author> authorByName, Map<String, Publisher> publisherByName) throws RuntimeException {
        List<Book> books;
        String seedDataJSON;
        try {
            seedDataJSON = Util.loadFixture(FIXTURE_PATH, "book_seed0.json");
            books = MAPPER.readValue(seedDataJSON, new TypeReference<>() {
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        books.forEach(book -> book.setId(null));
        List<Book> booksToSave = new ArrayList<>();

        for (Book book : books) {
            List<Author> authors = new ArrayList<>();
            for(Author a : book.getAuthors()) {
                String authorKey = a.getFirstName() + " " + a.getLastName();
                authors.add(authorByName.get(authorKey));
            }

            Publisher publisher = publisherByName.get(book.getPublisher().getName());

            if(publisher == null || authors.isEmpty() || authors.contains(null))
                throw new RuntimeException(JSON_STRUCTURE_ERROR_MESSAGE);

            book.setPublisher(publisher);
            book.setAuthors(authors);

            booksToSave.add(book);
        }

        repo.saveAll(booksToSave);
    }
}
