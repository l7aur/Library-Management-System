package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.exceptions.PublisherNotFoundException;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.dto.BookCreateDTO;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repository.AuthorRepository;
import com.laur.bookshop.repository.BookRepository;
import com.laur.bookshop.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(BookCreateDTO book) {
        Publisher publisher = publisherRepository.findById(book.getPublisherId()).orElseThrow(
                () -> new PublisherNotFoundException("Publisher " + book.getPublisherId() + " not found")
        );
        List<Author> authors = new ArrayList<>();
        for(UUID authorId : book.getAuthorIds()) {
            authors.add(authorRepository.findById(authorId).orElseThrow(
                    () -> new AuthorNotFoundException("Author with name " + authorId + " not found")
            ));
        }
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setAuthors(authors);
        newBook.setPublisher(publisher);
        newBook.setPublishYear(book.getPublishYear());
        newBook.setPrice(book.getPrice());
        newBook.setStock(book.getStock());
        return bookRepository.save(newBook);
    }

    public ResponseEntity<String> deleteBooksById(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body("No IDs provided");
        for(UUID i : ids)
            bookRepository.deleteById(i);
        return ResponseEntity.ok("Books deleted successfully");
    }

        public Book updateBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId()).orElseThrow(
                () -> new BookNotFoundException("Book with ISBN " + book.getId() + " not found")
        );
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthors(book.getAuthors());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setPublishYear(book.getPublishYear());
        return bookRepository.save(existingBook);
    }

//    public Book findBookByTitle(String title) {
//        return bookRepository.findByTitle(title).orElseThrow(
//                () -> new BookNotFoundException("Book with ISBN " + title + " not found")
//        );
//    }
//
//    public Book findBookByISBN(String isbn) {
//        return bookRepository.findByIsbn(isbn).orElseThrow(
//                () -> new BookNotFoundException("Book with ISBN " + isbn + " not found")
//        );
//    }
//
//    public List<Book> findBookByAuthor(String author) {
//        return bookRepository.findByAuthor(author).orElseThrow(
//                () -> new BookNotFoundException("Book with author " + author + " not found")
//        );
//    }
//
//
//    public void deleteBookByIsbn(String isbn) {
//        bookRepository.deleteByIsbn(isbn);
//    }
//
//    public void deleteBookByTitle(String title) {
//        bookRepository.deleteByTitle(title);
//    }
}
