package com.laur.bookshop.controller;

import com.laur.bookshop.model.data.Author;
import com.laur.bookshop.services.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) { this.authorService = authorService; }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {return authorService.findAllAuthors(); }

    @GetMapping("/authors/{firstName}{lastName}")
    public Author getAuthor(@PathVariable String firstName, @PathVariable String lastName) {
        return authorService.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/authors/{firstName}")
    public List<Author> getAuthorByFirstName(@PathVariable String firstName) {
        return authorService.findAuthorsByFirstName(firstName);
    }

    @GetMapping("/authors/{lastName}")
    public List<Author> getAuthorByLastName(@PathVariable String lastName) {
        return authorService.findAuthorsByLastName(lastName);
    }

    @GetMapping("/authors/{nationality}")
    public List<Author> getAuthorByNationality(@PathVariable String nationality) {
        return authorService.findAuthorsByNationality(nationality);
    }

    @PutMapping("/authors/{firstName}{lastName}")
    public Author updateAuthor(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Author author) {
        return authorService.updateAuthor(firstName, lastName, author);
    }

    @DeleteMapping("/authors/{firstName}{lastName}")
    public void deleteAuthor(@PathVariable String firstName, @PathVariable String lastName) {
        authorService.deleteAuthorByFirstNameAndLastName(firstName, lastName);
    }
}
