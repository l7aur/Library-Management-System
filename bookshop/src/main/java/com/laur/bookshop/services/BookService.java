package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.config.exceptions.PublisherNotFoundException;
import com.laur.bookshop.config.dto.BookDTO;
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
import java.util.stream.Collectors;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final PublisherRepo publisherRepo;

    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book addBook(BookDTO b) {
        Publisher publisher = publisherRepo.findById(UUID.fromString(b.getPublisherId())).orElseThrow(
                () -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND_MESSAGE)
        );
        if(publisher == null)
            throw new PublisherNotFoundException(PUBLISHER_NOT_FOUND_MESSAGE);

        List<Author> authors = new ArrayList<>();
        for(String id : b.getAuthorIds()) {
            authors.add(authorRepo.findById(UUID.fromString(id)).orElseThrow(
                    () -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE)
            ));
        }
        if(authors.isEmpty())
            throw new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE);

        if(bookRepo.findByIsbn(b.getIsbn()).isPresent())
            throw new DuplicateException(BOOK_DUPLICATE_MESSAGE);

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
            return ResponseEntity.badRequest().body(BOOK_DELETE_ERROR_MESSAGE);
        for(UUID i : ids)
            bookRepo.deleteById(i);
        return ResponseEntity.ok(BOOK_DELETE_SUCCESS_MESSAGE);
    }

    public Book updateBook(BookDTO b) {
        Book book = bookRepo.findById(b.getId()).orElseThrow(
                () -> new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE)
        );
        book.setTitle(b.getTitle());
        book.setAuthors(b.getAuthorIds().stream().map(id -> authorRepo.findById(UUID.fromString(id))
                        .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE)))
                .collect(Collectors.toList()));
        book.setStock(b.getStock());
        book.setIsbn(b.getIsbn());
        book.setPrice(b.getPrice());
        book.setPublishYear(b.getPublishYear());
        book.setPublisher(publisherRepo.findById(UUID.fromString(b.getPublisherId())).
                orElseThrow( () -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND_MESSAGE)));
        return bookRepo.save(book);
    }
}
