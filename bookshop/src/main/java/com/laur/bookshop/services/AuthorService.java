package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.dto.AuthorDTO;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.repositories.AuthorRepo;
import com.laur.bookshop.repositories.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public List<Author> findAllAuthors() {
        return authorRepo.findAll();
    }

    public Author addAuthor(AuthorDTO a) {
        List<Book> books = new ArrayList<>();
        for (String title : a.getBooks()) {
            books.add(bookRepo.findByTitle(title).orElseThrow(
                    () -> new BookNotFoundException(title + " not found!")
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
            return ResponseEntity.badRequest().body("No IDs provided!");
        for(UUID i : ids)
            authorRepo.deleteById(i);
        return ResponseEntity.ok("Authors deleted successfully!");
    }

    public Author updateAuthor(Author a) {
        Author author = authorRepo.findById(a.getId()).orElseThrow(
                () -> new AuthorNotFoundException(a.getFirstName() + " " + a.getLastName() + " not found!")
        );
        author.setFirstName(a.getFirstName());
        author.setLastName(a.getLastName());
        author.setNationality(a.getNationality());
        author.setAlias(a.getAlias());
        author.setBooks(a.getBooks());
        return authorRepo.save(author);
    }
}
