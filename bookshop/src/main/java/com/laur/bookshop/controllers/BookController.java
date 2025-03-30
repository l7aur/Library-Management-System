package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.BookDTO;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.services.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class BookController {
    private final BookService service;

    @GetMapping("/books/all")
    public List<Book> getAllBooks() {
        return service.findAllBooks();
    }

    @PostMapping("/books/add")
    public Book addBook(@Valid @RequestBody BookDTO validatedBook) {
        return service.addBook(validatedBook);
    }

    @DeleteMapping("/books/delete")
    public ResponseEntity<String> deleteBooks(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return service.deleteByIds(idList);
    }
    @PutMapping("/books/edit")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }
}
