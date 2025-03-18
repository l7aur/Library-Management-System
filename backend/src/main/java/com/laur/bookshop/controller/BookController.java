package com.laur.bookshop.controller;

import com.laur.bookshop.model.Book;
import com.laur.bookshop.dto.BookCreateDTO;
import com.laur.bookshop.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/all")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @PostMapping("/books/add")
    public Book addBook(@Valid @RequestBody BookCreateDTO bookDTO) {
        return bookService.addBook(bookDTO);
    }

    @DeleteMapping("/books/delete")
    public ResponseEntity<String> deleteBooksById(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return bookService.deleteBooksById(idList);
    }

//    @GetMapping("/books/isbn/{isbn}")
//    public Book getBookByISBN(@PathVariable String isbn) {
//        return bookService.findBookByISBN(isbn);
//    }
//
//    @GetMapping("/books/author/{author}")
//    public List<Book> getBookByAuthor(@PathVariable String author) {
//        return bookService.findBookByAuthor(author);
//    }
//
//    @GetMapping("/books/{title}")
//    public Book getBookByTitle(@PathVariable String title) {
//        return bookService.findBookByTitle(title);
//    }
//
//    @PutMapping("/books/{isbn}")
//    public Book updateBook(@PathVariable String isbn, @RequestBody Book book) {
//        return bookService.updateBook(isbn, book);
//    }
//
//    @DeleteMapping("/books/{isbn}")
//    public void deleteBookByIsbn(@PathVariable String isbn) {
//        bookService.deleteBookByIsbn(isbn);
//    }
//
//    @DeleteMapping("/books/{title}")
//    public void deleteBookByTitle(@PathVariable String title) {
//        bookService.deleteBookByTitle(title);
//    }
}
