package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.config.exceptions.PublisherNotFoundException;
import com.laur.bookshop.config.validators.model.BookValidator;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.PublisherRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final PublisherRepo publisherRepo;

    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book addBook(BookValidator b) {
        Publisher publisher = publisherRepo.findById(b.getPublisherId()).orElseThrow(
                () -> new PublisherNotFoundException("Publisher " + b.getPublisherId() + " not found!")
        );
        List<Author> authors = new ArrayList<>();
        for(UUID id : b.getAuthorIds()) {
            authors.add(authorRepo.findById(id).orElseThrow(
                    () -> new AuthorNotFoundException("Author " + id + " not found!")
            ));
        }
        if(bookRepo.findByIsbn(b.getIsbn()).isPresent())
            throw new DuplicateException("Book with the same ISBN already exists!");
        Book book = new Book();
        book.setIsbn(b.getIsbn());
        book.setTitle(b.getTitle());
        book.setAuthors(authors);
        book.setPublishYear(b.getPublishYear());
        book.setPrice(b.getPrice());
        book.setStock(b.getStock());
        book.setPublisher(publisher);
        return bookRepo.save(book);
    }

    public ResponseEntity<String> deleteByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body("No IDs provided");
        for(UUID i : ids)
            bookRepo.deleteById(i);
        return ResponseEntity.ok("Books deleted successfully");
    }

    public Book updateBook(Book b) {
        Book book = bookRepo.findById(b.getId()).orElseThrow(
                () -> new BookNotFoundException("Book " + b.getId() + " not found!")
        );
        book.setTitle(b.getTitle());
        book.setAuthors(b.getAuthors());
        book.setStock(b.getStock());
        book.setIsbn(b.getIsbn());
        book.setPrice(b.getPrice());
        book.setPublishYear(b.getPublishYear());
        book.setPublisher(b.getPublisher());
        return bookRepo.save(book);
    }
}
