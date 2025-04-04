package com.laur.bookshop.service;

import com.laur.bookshop.model.Author;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.services.AuthorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorServiceTests {

    @Mock
    private AuthorRepo repo;

    @InjectMocks
    private AuthorService service;

    private AutoCloseable autoCloseable;

    private static Integer NUMBER_OF_AUTHORS = 10;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testFindAll() {
        // given
        List<Author> authors = generateAuthors();

        // when
        when(repo.findAll()).thenReturn(authors);
        List<Author> result = service.findAllAuthors();

        // then
        assertEquals(NUMBER_OF_AUTHORS, result.size());
        verify(repo, times(1)).findAll();
        assertEquals(authors, result);
    }

    @Test
    public void testAdd() {
        // given

        // when

        // then
    }

    @Test
    public void testDeleteOne() {
        // given

        // when

        // then
    }

    @Test
    public void testDeleteMany() {
        // given

        // when

        // then
    }

    @Test
    public void testUpdate() {
        // given

        // when

        // then
    }

    private List<Author> generateAuthors() {
        NUMBER_OF_AUTHORS = 10;
        return List.of(
                // 1
                new Author(),
                // 2
                new Author(),
                // 3
                new Author(),
                // 4
                new Author(),
                // 5
                new Author(),
                // 6
                new Author(),
                // 7
                new Author(),
                // 8
                new Author(),
                // 9
                new Author(),
                // 10
                new Author()
        );
    }
}
