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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorServiceTests {

    @Mock
    private AuthorRepo repo;

    @InjectMocks
    private AuthorService service;

    private AutoCloseable autoCloseable;

    private static final Integer NUMBER_OF_AUTHORS = 10;

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
        List<Author> authors = generateAuthors();
        Author newAuthor = createNewAuthor();
        authors.add(newAuthor);

        // when
        when(repo.save(newAuthor)).thenReturn(newAuthor);
        Author result = service.addAuthor(newAuthor.toDTO());

        // then
        verify(repo, times(1)).save(newAuthor);
        assertEquals(newAuthor, result);
    }

    @Test
    public void testDeleteOne1() {
        // given
        List<Author> authors = generateAuthors();
        Author deletedAuthor = authors.getFirst();
        authors.remove(deletedAuthor);

        // when
        doNothing().when(repo).deleteById(deletedAuthor.getId());
        service.deleteByIds(List.of(deletedAuthor.getId()));
        when(repo.findAll()).thenReturn(authors);

        // then
        verify(repo, times(1)).deleteById(deletedAuthor.getId());
        assertEquals(repo.findAll(), authors);
    }

    @Test
    public void testDeleteOne2() {
        // given
        List<Author> authors = generateAuthors();
        Author deletedAuthor = authors.getLast();
        authors.remove(deletedAuthor);

        // when
        doNothing().when(repo).deleteById(deletedAuthor.getId());
        service.deleteByIds(List.of(deletedAuthor.getId()));
        when(repo.findAll()).thenReturn(authors);

        // then
        verify(repo, times(1)).deleteById(deletedAuthor.getId());
        assertEquals(repo.findAll(), authors);
    }

    @Test
    public void testDeleteMany() {
        // given
        List<Author> authors = generateAuthors();
        List<Author> deletedAuthors= List.of(
                authors.getFirst(),
                authors.getLast(),
                authors.get(3)
        );
        authors.removeAll(deletedAuthors);

        // when
        for(Author author : deletedAuthors) {
            doNothing().when(repo).deleteById(author.getId());
        }
        service.deleteByIds(deletedAuthors.stream().map(Author::getId).toList());
        when(repo.findAll()).thenReturn(authors);

        // then
        for (Author deleted : deletedAuthors) {
            verify(repo, times(1)).deleteById(deleted.getId());
        }
        assertEquals(authors, repo.findAll());
    }

    @Test
    public void testUpdate() {
        // given
        UUID authorId = UUID.randomUUID();
        Author existingAuthor = createNewAuthor(authorId);
        Author updatedAuthor = createUpdatedAuthor(authorId);

        // when
        when(repo.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(repo.save(updatedAuthor)).thenReturn(updatedAuthor);
        Author result = service.updateAuthor(updatedAuthor);

        // then
        verify(repo, times(1)).findById(authorId);
        verify(repo, times(1)).save(updatedAuthor);
        assertEquals(updatedAuthor, result);
    }

    private List<Author> generateAuthors() {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_AUTHORS; i++) {
            Author author = new Author();
            author.setId(UUID.randomUUID());
            author.setAlias("Alias" + i);
            author.setNationality("Nationality" + i);
            author.setFirstName("FirstName" + i);
            author.setLastName("LastName" + i);
            author.setBooks(Collections.emptyList());
            authors.add(author);
        }
        return authors;
    }

    private Author createNewAuthor() {
        Author newAuthor = new Author();
        newAuthor.setAlias("NewAlias");
        newAuthor.setFirstName("NewFirstName");
        newAuthor.setLastName("NewLastName");
        newAuthor.setBooks(Collections.emptyList());
        newAuthor.setNationality("NewNationality");
        return newAuthor;
    }

    private Author createNewAuthor(UUID authorId) {
        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);
        existingAuthor.setNationality("ExistingNationality");
        existingAuthor.setBooks(Collections.emptyList());
        existingAuthor.setLastName("ExistingLastName");
        existingAuthor.setFirstName("ExistingFirstName");
        existingAuthor.setAlias("ExistingAlias");
        return existingAuthor;
    }

    private Author createUpdatedAuthor(UUID authorId) {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorId);
        updatedAuthor.setNationality("UpdatedNationality");
        updatedAuthor.setBooks(Collections.emptyList());
        updatedAuthor.setLastName("UpdatedLastName");
        updatedAuthor.setFirstName("UpdatedFirstName");
        updatedAuthor.setAlias("UpdatedAlias");
        return updatedAuthor;
    }
}
