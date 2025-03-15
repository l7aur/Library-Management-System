package com.laur.bookshop.controller;

import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.BookCreateDTO;
import com.laur.bookshop.services.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/books/isbn/{isbn}")
    public Book getBookByISBN(@PathVariable String isbn) {
        return bookService.findBookByISBN(isbn);
    }

    @GetMapping("/books/author/{author}")
    public List<Book> getBookByAuthor(@PathVariable String author) {
        return bookService.findBookByAuthor(author);
    }

    @GetMapping("/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @PostMapping("/books/add")
    public Book addBook(@Valid @RequestBody BookCreateDTO bookDTO) {
        return bookService.addBook(bookDTO);
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
