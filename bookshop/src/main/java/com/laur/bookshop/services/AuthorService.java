package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.dto.AuthorDTO;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.repositories.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public List<Author> findAllAuthors() {
        return authorRepo.findAll();
    }

    public Author addAuthor(AuthorDTO a) {
        if(authorRepo.findByFullName(a.getFirstName(), a.getLastName()).isPresent())
            throw new DuplicateException(AUTHOR_DUPLICATE_MESSAGE);

        List<Book> books = new ArrayList<>();
        for (String id : a.getBookIDs()) {
            books.add(bookRepo.findByTitle(id).orElseThrow(
                    () -> new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE)
            ));
        }
        Author author = new Author();
        author.setBooks(books);
        author.setAlias(a.getAlias());
        author.setFirstName(a.getFirstName());
        author.setLastName(a.getLastName());
        author.setNationality(a.getNationality());
        return authorRepo.save(author);
    }

    public ResponseEntity<String> deleteByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body(AUTHOR_DELETE_ERROR_MESSAGE);
        for(UUID i : ids)
            authorRepo.deleteById(i);
        return ResponseEntity.ok(AUTHOR_DELETE_SUCCESS_MESSAGE);
    }

    public Author updateAuthor(AuthorDTO a) {
        Author author = authorRepo.findById(a.getId()).orElseThrow(
                () -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE)
        );
        author.setFirstName(a.getFirstName());
        author.setLastName(a.getLastName());
        author.setNationality(a.getNationality());
        author.setAlias(a.getAlias());
        author.setBooks(a.getBookIDs().stream()
                .map(id -> bookRepo.findById(UUID.fromString(id)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
        return authorRepo.save(author);
    }
}
