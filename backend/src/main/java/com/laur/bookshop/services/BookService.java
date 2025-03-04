package com.laur.bookshop.services;

import com.laur.bookshop.model.data.Book;
import com.laur.bookshop.repository.data.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + title + " not found")
        );
    }

    public Book findBookByISBN(String isbn) {
        return bookRepository.findByISBN(isbn).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + isbn + " not found")
        );
    }

    public Book findBookByAuthor(String author) {
        return bookRepository.findByAuthor(author).orElseThrow(
                () -> new IllegalStateException("Book with author " + author + " not found")
        );
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(String isbn, Book book) {
        Book exitingBook = bookRepository.findByISBN(isbn).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + isbn + " not found")
        );
        exitingBook.setTitle(book.getTitle());
        exitingBook.setAuthors(book.getAuthors());
        exitingBook.setIsbn(book.getIsbn());
        exitingBook.setPublisher(book.getPublisher());
        exitingBook.setPublishYear(book.getPublishYear());
        return bookRepository.save(exitingBook);
    }

    public void deleteBookByISBN(String isbn) {
        bookRepository.deleteByISBN(isbn);
    }

    public void deleteBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }
}
