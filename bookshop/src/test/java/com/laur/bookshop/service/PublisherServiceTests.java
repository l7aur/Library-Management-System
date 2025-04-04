package com.laur.bookshop.service;

import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.PublisherRepo;
import com.laur.bookshop.services.PublisherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PublisherServiceTests {

    @Mock
    private PublisherRepo repo;

    @InjectMocks
    private PublisherService service;

    private AutoCloseable autoCloseable;

    private static Integer NUMBER_OF_PUBLISHERS = 10;

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
        List<Publisher> publishers = generatePublishers();

        // when
        when(repo.findAll()).thenReturn(publishers);
        List<Publisher> result = service.findAllPublishers();

        // then
        assertEquals(NUMBER_OF_PUBLISHERS, result.size());
        verify(repo, times(1)).findAll();
        assertEquals(publishers, result);
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

    private List<Publisher> generatePublishers() {
        NUMBER_OF_PUBLISHERS = 10;
        return List.of(
                // 1
                new Publisher(),
                // 2
                new Publisher(),
                // 3
                new Publisher(),
                // 4
                new Publisher(),
                // 5
                new Publisher(),
                // 6
                new Publisher(),
                // 7
                new Publisher(),
                // 8
                new Publisher(),
                // 9
                new Publisher(),
                // 10
                new Publisher()
        );
    }
}
