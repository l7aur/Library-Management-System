package com.laur.bookshop.controller;

import com.laur.bookshop.model.data.Book;
import com.laur.bookshop.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/books/{isbn}")
    public Book getBookByISBN(@PathVariable String isbn) {
        return bookService.findBookByISBN(isbn);
    }

    @GetMapping("/books/{author}")
    public List<Book> getBookByAuthor(@PathVariable String author) {
        return bookService.findBookByAuthor(author);
    }

    @GetMapping("/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/books/{isbn}")
    public Book updateBook(@PathVariable String isbn, @RequestBody Book book) {
        return bookService.updateBook(isbn, book);
    }

    @DeleteMapping("/books/{isbn}")
    public void deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }

    @DeleteMapping("/books/{title}")
    public void deleteBookByTitle(@PathVariable String title) {
        bookService.deleteBookByTitle(title);
    }
}
