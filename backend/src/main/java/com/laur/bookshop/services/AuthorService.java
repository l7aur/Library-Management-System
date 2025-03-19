package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.AuthorNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.model.Author;
import com.laur.bookshop.dto.AuthorCreateDTO;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.repository.AuthorRepository;
import com.laur.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(AuthorCreateDTO author) {
        List<Book> books = new ArrayList<>();
        for(String book : author.getBooks()) {
            books.add(bookRepository.findByTitle(book).orElseThrow(
                    () -> new BookNotFoundException("No book found with title: " + book)
            ));
        }
        Author newAuthor = new Author();
        newAuthor.setFirstName(author.getFirstName());
        newAuthor.setLastName(author.getLastName());
        newAuthor.setNationality(author.getNationality());
        newAuthor.setAlias(author.getAlias());
        newAuthor.setBooks(books);
        return authorRepository.save(newAuthor);
    }

    public ResponseEntity<String> deleteAuthorsById(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body("No IDs provided");
        for(UUID i : ids)
            authorRepository.deleteById(i);
        return ResponseEntity.ok("Authors deleted successfully");
    }

    public Author updateAuthor(Author author) {
        Author existingAuthor = authorRepository.findById(author.getId()).orElseThrow(
                () -> new AuthorNotFoundException("No author found with id: " + author.getId())
        );
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());
        existingAuthor.setNationality(author.getNationality());
        return authorRepository.save(existingAuthor);
    }

//    public List<Author> findAuthorsByFirstName(String firstName) {
//        return  authorRepository.findByFirstName(firstName).orElseThrow(
//                () -> new AuthorNotFoundException("No author found with firstName: " + firstName)
//        );
//    }
//
//    public List<Author> findAuthorsByLastName(String lastName) {
//        return  authorRepository.findByLastName(lastName).orElseThrow(
//                () -> new AuthorNotFoundException("No author found with lastName: " + lastName)
//        );
//    }
//
//    public Author findAuthorByFirstNameAndLastName(String firstName, String lastName) {
//        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(
//                () -> new AuthorNotFoundException("No author found with firstName: " + firstName + " and lastName: " + lastName)
//        );
//    }
//
//    public List<Author> findAuthorsByNationality(String nationality) {
//        return authorRepository.findByNationality(nationality).orElseThrow(
//                () -> new AuthorNotFoundException("No author found with nationality: " + nationality)
//        );
//    }


//
//    public void deleteAuthorByFirstNameAndLastName(String firstName, String lastName) {
//        authorRepository.deleteByFirstNameAndLastName(firstName, lastName);
//    }
}
