package com.laur.bookshop.services;

import com.laur.bookshop.model.Book;
import com.laur.bookshop.repository.BookRepository;
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
        return bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new IllegalStateException("Book with ISBN " + isbn + " not found")
        );
    }

    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findByAuthor(author).orElseThrow(
                () -> new IllegalStateException("Book with author " + author + " not found")
        );
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
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
