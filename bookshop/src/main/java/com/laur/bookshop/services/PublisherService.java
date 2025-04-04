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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
            throw new DuplicateException(p.getName() + " already exists!");

        List<Book> books = new ArrayList<>();
        for(String id : p.getBooks()) {
            books.add(bookRepo.findByTitle(id).orElseThrow(
                    () -> new BookNotFoundException(id + " not found!")
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
            return ResponseEntity.badRequest().body("No IDs provided!");
        for(UUID i : ids)
            publisherRepo.deleteById(i);
        return ResponseEntity.ok("Publishers deleted successfully!");
    }

    public Publisher updatePublisher(Publisher p) {
        Publisher publisher = publisherRepo.findById(p.getId()).orElseThrow(
                () -> new PublisherNotFoundException(p.getName() + " not found!")
        );
        publisher.setName(p.getName());
        publisher.setLocation(p.getLocation());
        publisher.setFoundingYear(p.getFoundingYear());
        return publisherRepo.save(publisher);
    }

    public Publisher findPublisherByName(String name) {
        return publisherRepo.findByName(name).orElseThrow(
                () -> new PublisherNotFoundException(name + " not found!")
         );
    }
}
