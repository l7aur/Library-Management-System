package com.laur.bookshop.services;

import com.laur.bookshop.model.Author;
import com.laur.bookshop.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorsByFirstName(String firstName) {
        return  authorRepository.findByFirstName(firstName).orElseThrow(
                () -> new IllegalStateException("No author found with firstName: " + firstName)
        );
    }

    public List<Author> findAuthorsByLastName(String lastName) {
        return  authorRepository.findByLastName(lastName).orElseThrow(
                () -> new IllegalStateException("No author found with lastName: " + lastName)
        );
    }

    public Author findAuthorByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(
                () -> new IllegalStateException("No author found with firstName: " + firstName + " and lastName: " + lastName)
        );
    }

    public List<Author> findAuthorsByNationality(String nationality) {
        return authorRepository.findByNationality(nationality).orElseThrow(
                () -> new IllegalStateException("No author found with nationality: " + nationality)
        );
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(String firstName, String lastName, Author author) {
        Author existingAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(
                () -> new IllegalStateException("No author found with firstName: " + firstName + " and lastName: " + lastName)
        );
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());
        existingAuthor.setNationality(author.getNationality());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthorByFirstNameAndLastName(String firstName, String lastName) {
        authorRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }
}
