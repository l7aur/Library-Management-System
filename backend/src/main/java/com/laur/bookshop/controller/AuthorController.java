package com.laur.bookshop.controller;

import com.laur.bookshop.model.Author;
import com.laur.bookshop.dto.AuthorCreateDTO;
import com.laur.bookshop.services.AuthorService;
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
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors/all")
    public List<Author> getAllAuthors() {return authorService.findAllAuthors(); }

    @PostMapping("/authors/add")
    public Author addAuthor(@Valid @RequestBody AuthorCreateDTO authorDTO) {
        return authorService.addAuthor(authorDTO);
    }

    @DeleteMapping("/authors/delete")
    public ResponseEntity<String> deleteAuthor(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return authorService.deleteAuthorsById(idList);
    }
//    @GetMapping("/authors/{firstName}/{lastName}")
//    public Author getAuthor(@PathVariable String firstName, @PathVariable String lastName) {
//        return authorService.findAuthorByFirstNameAndLastName(firstName, lastName);
//    }

//    @GetMapping("/authors/f{firstName}")
//    public List<Author> getAuthorByFirstName(@PathVariable String firstName) {
//        return authorService.findAuthorsByFirstName(firstName);
//    }

//    @GetMapping("/authors/l{lastName}")
//    public List<Author> getAuthorByLastName(@PathVariable String lastName) {
//        return authorService.findAuthorsByLastName(lastName);
//    }

//    @GetMapping("/authors/{nationality}")
//    public List<Author> getAuthorByNationality(@PathVariable String nationality) {
//        return authorService.findAuthorsByNationality(nationality);
//    }


//    @PutMapping("/authors/{firstName}/{lastName}")
//    public Author updateAuthor(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Author author) {
//        return authorService.updateAuthor(firstName, lastName, author);
//    }
}
