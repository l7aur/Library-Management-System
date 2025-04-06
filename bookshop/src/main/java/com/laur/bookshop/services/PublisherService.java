package com.laur.bookshop.services;

import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.config.exceptions.DuplicateException;
import com.laur.bookshop.config.exceptions.PublisherNotFoundException;
import com.laur.bookshop.config.dto.PublisherDTO;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.PublisherRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.laur.bookshop.config.enums.AppMessages.*;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepo publisherRepo;
    private final BookRepo bookRepo;

    public List<Publisher> findAllPublishers() {
        return publisherRepo.findAll();
    }

    public Publisher addPublisher(PublisherDTO p) {
        if(publisherRepo.findByName(p.getName()).isPresent())
            throw new DuplicateException(PUBLISHER_DUPLICATE_MESSAGE);

        List<Book> books = new ArrayList<>();
        for(String id : p.getBookIds()) {
            books.add(bookRepo.findByTitle(id).orElseThrow(
                    () -> new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE)
            ));
        }
        Publisher publisher = new Publisher();
        publisher.setName(p.getName());
        publisher.setLocation(p.getLocation());
        publisher.setFoundingYear(p.getFoundingYear());
        publisher.setBooks(books);
        return publisherRepo.save(publisher);
    }

    public ResponseEntity<String> deleteByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return ResponseEntity.badRequest().body(PUBLISHER_DELETE_ERROR_MESSAGE);
        for(UUID i : ids)
            publisherRepo.deleteById(i);
        return ResponseEntity.ok(PUBLISHER_DELETE_SUCCESS_MESSAGE);
    }

    public Publisher updatePublisher(PublisherDTO p) {
        Publisher publisher = publisherRepo.findById(p.getId()).orElseThrow(
                () -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND_MESSAGE)
        );
        publisher.setName(p.getName());
        publisher.setLocation(p.getLocation());
        publisher.setFoundingYear(p.getFoundingYear());
        return publisherRepo.save(publisher);
    }

    public Publisher findPublisherByName(String name) {
        return publisherRepo.findByName(name).orElseThrow(
                () -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND_MESSAGE)
         );
    }
}
