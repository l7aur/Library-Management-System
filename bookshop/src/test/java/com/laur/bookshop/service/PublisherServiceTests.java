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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PublisherServiceTests {

    @Mock
    private PublisherRepo repo;
    private static final Integer NUMBER_OF_PUBLISHERS = 10;

    @InjectMocks
    private PublisherService service;

    private AutoCloseable autoCloseable;

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
        List<Publisher> publishers = generatePublishers();

        Publisher newPublisher = createNewPublisher();
        publishers.add(newPublisher);

        // when
        when(repo.save(newPublisher)).thenReturn(newPublisher);
        Publisher result = service.addPublisher(newPublisher.toDTO());

        // then
        verify(repo, times(1)).save(newPublisher);
        assertEquals(newPublisher, result);
    }

    @Test
    public void testDeleteOne1() {
        // given
        List<Publisher> publishers = generatePublishers();
        Publisher deletedPublisher = publishers.getFirst();
        publishers.remove(deletedPublisher);

        // when
        doNothing().when(repo).deleteById(deletedPublisher.getId());
        service.deleteByIds(List.of(deletedPublisher.getId()));
        when(repo.findAll()).thenReturn(publishers);

        // then
        verify(repo, times(1)).deleteById(deletedPublisher.getId());
        assertEquals(repo.findAll(), publishers);
    }

    @Test
    public void testDeleteOne2() {
        // given
        List<Publisher> publishers = generatePublishers();
        Publisher deletedPublisher = publishers.getLast();
        publishers.remove(deletedPublisher);

        // when
        doNothing().when(repo).deleteById(deletedPublisher.getId());
        service.deleteByIds(List.of(deletedPublisher.getId()));
        when(repo.findAll()).thenReturn(publishers);

        // then
        verify(repo, times(1)).deleteById(deletedPublisher.getId());
        assertEquals(repo.findAll(), publishers);
    }

    @Test
    public void testDeleteMany() {
        // given
        List<Publisher> publishers = generatePublishers();
        List<Publisher> deletedPublishers = List.of(
                publishers.getFirst(),
                publishers.getLast(),
                publishers.get(5)
        );
        publishers.removeAll(deletedPublishers);

        // when
        for(Publisher publisher : deletedPublishers) {
            doNothing().when(repo).deleteById(publisher.getId());
        }
        service.deleteByIds(deletedPublishers.stream().map(Publisher::getId).toList());
        when(repo.findAll()).thenReturn(publishers);

        // then
        for (Publisher deleted : deletedPublishers) {
            verify(repo, times(1)).deleteById(deleted.getId());
        }
        assertEquals(publishers, repo.findAll());
    }

    @Test
    public void testUpdate() {
        // given
        UUID publisherId = UUID.randomUUID();
        Publisher existingPublisher = createNewPublisher(publisherId);
        Publisher updatedPublisher = createUpdatedPublisher(publisherId);

        // when
        when(repo.findById(publisherId)).thenReturn(Optional.of(existingPublisher));
        when(repo.save(updatedPublisher)).thenReturn(updatedPublisher);
        Publisher result = service.updatePublisher(updatedPublisher);

        // then
        verify(repo, times(1)).findById(publisherId);
        verify(repo, times(1)).save(updatedPublisher);
        assertEquals(updatedPublisher, result);
    }

    private List<Publisher> generatePublishers() {
        List<Publisher> publishers = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_PUBLISHERS; i++) {
            Publisher publisher = new Publisher();
            publisher.setId(UUID.randomUUID());
            publisher.setName("Publisher " + i);
            publisher.setLocation("Location " + i);
            publisher.setBooks(Collections.emptyList());
            publisher.setFoundingYear(i);
            publishers.add(publisher);
        }
        return publishers;
    }

    private Publisher createNewPublisher() {
        Publisher newPublisher = new Publisher();
        newPublisher.setLocation("Location" + NUMBER_OF_PUBLISHERS);
        newPublisher.setName("Name" + NUMBER_OF_PUBLISHERS);
        newPublisher.setBooks(Collections.emptyList());
        newPublisher.setFoundingYear(NUMBER_OF_PUBLISHERS);
        return newPublisher;
    }

    private Publisher createNewPublisher(UUID publisherId) {
        Publisher existingPublisher = new Publisher();
        existingPublisher.setId(publisherId);
        existingPublisher.setLocation("Location" + 0);
        existingPublisher.setName("Name" + 0);
        existingPublisher.setBooks(Collections.emptyList());
        existingPublisher.setFoundingYear(0);
        return existingPublisher;
    }

    private Publisher createUpdatedPublisher(UUID publisherId) {
        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setId(publisherId);
        updatedPublisher.setLocation("Location" + 1);
        updatedPublisher.setName("Name" + 1);
        updatedPublisher.setBooks(Collections.emptyList());
        updatedPublisher.setFoundingYear(1);
        return updatedPublisher;
    }
}
