package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.AuthorDTO;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.services.AuthorService;
import jakarta.annotation.security.PermitAll;
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
    private final AuthorService service;

    @PermitAll
    @GetMapping("/authors/all")
    public List<Author> getAllAuthors() {return service.findAllAuthors(); }

    @PermitAll
    @PostMapping("/authors/add")
    public Author addAuthor(@Valid @RequestBody AuthorDTO validatedAuthor) {
        return service.addAuthor(validatedAuthor);
    }

    @PermitAll
    @DeleteMapping("/authors/delete")
    public ResponseEntity<String> deleteAuthors(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return service.deleteByIds(idList);
    }

    @PermitAll
    @PutMapping("/authors/edit")
    public Author updateAuthor(@Valid @RequestBody AuthorDTO author) {
        return service.updateAuthor(author);
    }
}
