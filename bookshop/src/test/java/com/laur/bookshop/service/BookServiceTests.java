package com.laur.bookshop.service;

import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.PublisherRepo;
import com.laur.bookshop.services.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceTests {

    @Mock
    private BookRepo repo;
    private static final Integer NUMBER_OF_BOOKS = 10;

    @Mock
    private PublisherRepo publisherRepo;
    private static final Integer NUMBER_OF_PUBLISHERS = 10;
    
    @Mock
    private AuthorRepo authorRepo;
    private static final Integer NUMBER_OF_AUTHORS = 10;

    @InjectMocks
    private BookService service;

    private AutoCloseable autoCloseable;
    private static final List<String> isbnList = List.of(
            "978-1-234567-89-1",
            "978-0-987654-32-1",
            "978-3-456789-12-3",
            "978-4-567890-11-2",
            "978-2-345678-90-4",
            "978-5-678901-23-6",
            "978-6-789012-34-5",
            "978-7-890123-45-7",
            "978-8-901234-56-8",
            "978-9-012345-67-9"
    );

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
    public void testAddAuthorsPublisher() {
        // given
        setupPublisherRepo();
        setupAuthorRepo();
        List<Book> books = generateBooks();
        Book newBook = createNewBook();
        books.add(newBook);

        // when
        when(repo.save(newBook)).thenReturn(newBook);
        Book result = service.addBook(newBook.toDTO());

        // then
        verify(repo, times(1)).save(newBook);
        assertEquals(newBook, result);
    }

    @Test
    public void testDeleteFirstNoAuthorNoPublisher() {
        // given
        List<Book> books = generateBooks();
        Book deletedBook = books.getFirst();
        books.remove(deletedBook);

        // when
        doNothing().when(repo).deleteById(deletedBook.getId());
        service.deleteByIds(List.of(deletedBook.getId()));
        when(repo.findAll()).thenReturn(books);

        // then
        verify(repo, times(1)).deleteById(deletedBook.getId());
        assertEquals(repo.findAll(), books);
    }

    @Test
    public void testDeleteLastNoAuthorNoPublisher() {
        // given
        List<Book> books = generateBooks();
        Book deletedBook = books.getLast();
        books.remove(deletedBook);

        // when
        doNothing().when(repo).deleteById(deletedBook.getId());
        service.deleteByIds(List.of(deletedBook.getId()));
        when(repo.findAll()).thenReturn(books);

        // then
        verify(repo, times(1)).deleteById(deletedBook.getId());
        assertEquals(repo.findAll(), books);
    }

    @Test
    public void testDeleteFirstSingleAuthorPublisher() {
        // given
        setupAuthorRepo();
        setupPublisherRepo();
        List<Book> books = generateBooksSingleAuthor(authorRepo, publisherRepo);
        Book deletedBook = books.getFirst();
        books.remove(deletedBook);

        // when
        doNothing().when(repo).deleteById(deletedBook.getId());
        service.deleteByIds(List.of(deletedBook.getId()));
        when(repo.findAll()).thenReturn(books);

        // then
        verify(repo, times(1)).deleteById(deletedBook.getId());
        assertEquals(repo.findAll(), books);
    }

    @Test
    public void testDeleteOneMultipleAuthorsPublisher() {
        // given
        setupAuthorRepo();
        setupPublisherRepo();
        List<Book> books = generateBooksMultipleAuthors(authorRepo, publisherRepo);
        Book deletedBook = books.getFirst();
        books.remove(deletedBook);

        // when
        doNothing().when(repo).deleteById(deletedBook.getId());
        service.deleteByIds(List.of(deletedBook.getId()));
        when(repo.findAll()).thenReturn(books);

        // then
        verify(repo, times(1)).deleteById(deletedBook.getId());
        assertEquals(repo.findAll(), books);
    }

    @Test
    public void testDeleteManyNoAuthorNoPublisher() {
        // given
        List<Book> books = generateBooks();
        List<Book> deletedBooks = List.of(
                books.getFirst(),
                books.getLast(),
                books.get(3)
        );
        books.removeAll(deletedBooks);

        // when
        for(Book book : deletedBooks) {
            doNothing().when(repo).deleteById(book.getId());
        }
        service.deleteByIds(deletedBooks.stream().map(Book::getId).toList());
        when(repo.findAll()).thenReturn(books);

        // then
        for (Book deleted : deletedBooks) {
            verify(repo, times(1)).deleteById(deleted.getId());
        }
        assertEquals(books, repo.findAll());
    }

    @Test
    public void testUpdateNoAuthorNoPublisher() {
        // given
        setupPublisherRepo();
        UUID bookId = UUID.randomUUID();
        Book existingBook = createExistingBook(bookId);
        Book updatedBook = createNewBook(bookId);

        // when
        when(repo.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(repo.save(updatedBook)).thenReturn(updatedBook);
        Book result = service.updateBook(updatedBook);

        // then
        verify(repo, times(1)).findById(bookId);
        verify(repo, times(1)).save(updatedBook);
        assertEquals(updatedBook, result);
    }

    private List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        
        for (int i = 0; i < NUMBER_OF_BOOKS; i++) {
            Book book = new Book();
            book.setId(UUID.randomUUID());
            book.setIsbn(isbnList.get(i));
            book.setTitle("Title" + i);
            book.setStock(i + 100);
            book.setAuthors(Collections.emptyList());
            book.setPublisher(new Publisher());
            book.setPrice((i + 10) * 10.99);
            book.setPublishYear(i + 1000);
            books.add(book);
        }
        return books;
    }

    private List<Book> generateBooksMultipleAuthors(AuthorRepo authorRepo, PublisherRepo publisherRepo) {
        List<Book> books = new ArrayList<>();
        List<Publisher> publishers = publisherRepo.findAll();
        List<Author> authors = authorRepo.findAll();
        for (int i = 0; i < NUMBER_OF_BOOKS - 1; i++) {
            Book book = createNewBook(i, publishers.get(i), List.of(authors.get(i), authors.get(i+1)));
            books.add(book);
        }
        return books;
    }

    private List<Book> generateBooksSingleAuthor(AuthorRepo authorRepo, PublisherRepo publisherRepo) {
        List<Book> books = new ArrayList<>();
        List<Publisher> publishers = publisherRepo.findAll();
        List<Author> authors = authorRepo.findAll();
        for (int i = 0; i < NUMBER_OF_BOOKS; i++) {
            Book book = createNewBook(i, publishers.get(i), List.of(authors.get(i)));
            books.add(book);
        }
        return books;
    }

    private void setupPublisherRepo() {
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
        when(publisherRepo.findAll()).thenReturn(publishers);
        for(int i = 0; i < NUMBER_OF_PUBLISHERS; i++) {
            when(publisherRepo.findById(publishers.get(i).getId())).thenReturn(Optional.of(publishers.get(i)));
        }
    }

    private void setupAuthorRepo() {
        List<Author> authors = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_AUTHORS; i++) {
            Author author = new Author();
            author.setId(UUID.randomUUID());
            author.setNationality("Nationality " + i);
            author.setAlias("Alias " + i);
            author.setFirstName("FirstName" + i);
            author.setLastName("LastName" + i);
            author.setBooks(Collections.emptyList());
            authors.add(author);
        }
        when(authorRepo.findAll()).thenReturn(authors);
        for(int i = 0; i < NUMBER_OF_AUTHORS; i++) {
            when(authorRepo.findById(authors.get(i).getId())).thenReturn(Optional.of(authors.get(i)));
        }
    }

    private Book createNewBook() {
        Book newBook = new Book();
        newBook.setIsbn("978-3-111222-33-4");
        newBook.setTitle("Title" + NUMBER_OF_BOOKS);
        newBook.setStock(NUMBER_OF_BOOKS + 100);
        newBook.setAuthors(Collections.emptyList());
        newBook.setPublisher(publisherRepo.findAll().getFirst());
        newBook.setPrice((NUMBER_OF_BOOKS + 10) * 10.99);
        newBook.setPublishYear(NUMBER_OF_BOOKS + 1000);
        return newBook;
    }

    private Book createNewBook(UUID bookId) {
        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setIsbn("978-9-012345-67-9");
        updatedBook.setTitle("Title" + NUMBER_OF_BOOKS);
        updatedBook.setStock(105);
        updatedBook.setAuthors(Collections.emptyList());
        updatedBook.setPublisher(publisherRepo.findAll().getFirst());
        updatedBook.setPrice(10.92);
        updatedBook.setPublishYear(2000);
        return updatedBook;
    }

    private Book createNewBook(int x, Publisher p, List<Author> as) {
        Book newBook = new Book();
        newBook.setId(UUID.randomUUID());
        newBook.setIsbn(isbnList.get(x));
        newBook.setTitle("Title" + x);
        newBook.setStock(x + 100);
        newBook.setAuthors(as);
        newBook.setPublisher(p);
        newBook.setPrice((x + 10) * 10.99);
        newBook.setPublishYear(x + 1000);
        return newBook;
    }

    private Book createExistingBook(UUID bookId) {
        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setIsbn("978-6-789012-34-5");
        existingBook.setTitle("UpdatedTitle");
        existingBook.setStock(100);
        existingBook.setAuthors(Collections.emptyList());
        existingBook.setPublisher(publisherRepo.findAll().getLast());
        existingBook.setPrice(10.99);
        existingBook.setPublishYear(1000);
        return existingBook;
    }
}
