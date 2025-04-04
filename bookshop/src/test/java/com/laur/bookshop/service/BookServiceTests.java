package com.laur.bookshop.service;

import com.laur.bookshop.model.Book;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.services.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceTests {

    @Mock
    private BookRepo repo;

    @InjectMocks
    private BookService service;

    private AutoCloseable autoCloseable;

    private static Integer NUMBER_OF_BOOKS = 10;

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
        List<Book> books = generateBooks();

        // when
        when(repo.findAll()).thenReturn(books);
        List<Book> result = service.findAllBooks();

        // then
        assertEquals(NUMBER_OF_BOOKS, result.size());
        verify(repo, times(1)).findAll();
        assertEquals(books, result);
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

    private List<Book> generateBooks() {
        NUMBER_OF_BOOKS = 10;
        return List.of(
                // 1
                new Book(),
                // 2
                new Book(),
                // 3
                new Book(),
                // 4
                new Book(),
                // 5
                new Book(),
                // 6
                new Book(),
                // 7
                new Book(),
                // 8
                new Book(),
                // 9
                new Book(),
                // 10
                new Book()
        );
    }
}
