package com.laur.bookshop.services;

import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.BookCreateDTO;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repository.AuthorRepository;
import com.laur.bookshop.repository.BookRepository;
import com.laur.bookshop.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + title + " not found")
        );
    }

    public Book findBookByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + isbn + " not found")
        );
    }

    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findByAuthor(author).orElseThrow(
                () -> new IllegalStateException("Book with author " + author + " not found")
        );
    }

    public Book addBook(BookCreateDTO book) {
        Publisher publisher = publisherRepository.findByName(book.getPublisher()).orElseThrow(
                () -> new IllegalStateException("Publisher " + book.getPublisher() + " not found")
        );
        List<Author> authors = new ArrayList<>();
        for(String author : book.getAuthors()) {
            String fName = author.split(" ")[0];
            String lName = author.split(" ")[1];
            authors.add(authorRepository.findByFirstNameAndLastName(fName, lName).orElseThrow(
                    () -> new IllegalStateException("Author with name " + author + " not found")
            ));
        }
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setAuthors(authors);
        newBook.setPublisher(publisher);
        newBook.setPublishYear(book.getPublishYear());
        return bookRepository.save(newBook);
    }

    public Book updateBook(String isbn, Book book) {
        Book existingBook = bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + isbn + " not found")
        );
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthors(book.getAuthors());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setPublishYear(book.getPublishYear());
        return bookRepository.save(existingBook);
    }

    public void deleteBookByIsbn(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public void deleteBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }
}
