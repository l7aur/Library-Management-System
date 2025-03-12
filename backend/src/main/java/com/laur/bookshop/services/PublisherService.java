package com.laur.bookshop.services;

import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.model.PublisherCreateDTO;
import com.laur.bookshop.repository.BookRepository;
import com.laur.bookshop.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public List<Publisher> findAllPublishers(){
        return publisherRepository.findAll();
    }

    public Publisher findPublisherByName(String name){
        return publisherRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException("No publisher found with name " + name)
        );
    }

    public List<Publisher> findPublisherByLocation(String location){
        return publisherRepository.findByLocation(location).orElseThrow(
                () -> new IllegalStateException("Publishers with location " + location + " not found")
        );
    }

    public List<Publisher> findPublisherByFoundingYear(int year){
        return publisherRepository.findByFoundingYear(year).orElseThrow(
                () -> new IllegalStateException("Publishers with founding year " + year + " not found")
        );
    }

    public Publisher addPublisher(PublisherCreateDTO publisher){
        List<Book> books = new ArrayList<>();
        for(String book : publisher.getBooks()){
            books.add(bookRepository.findByTitle(book).orElseThrow(
                    () -> new IllegalStateException("Book " + book + " not found")
            ));
        }
        Publisher newPublisher = new Publisher();
        newPublisher.setLocation(publisher.getLocation());
        newPublisher.setFoundingYear(publisher.getFoundingYear());
        newPublisher.setName(publisher.getName());
        newPublisher.setBooks(books);
        return publisherRepository.save(newPublisher);
    }

    public Publisher updatePublisher(String name, Publisher publisher){
        Publisher existingPublisher = publisherRepository.findByName(name).orElseThrow(
                () -> new IllegalStateException("Publisher with name " + publisher.getName() + " not found")
        );
        existingPublisher.setName(publisher.getName());
        existingPublisher.setFoundingYear(publisher.getFoundingYear());
        existingPublisher.setLocation(publisher.getLocation());
        return publisherRepository.save(existingPublisher);
    }

    public void deletePublisherByName(String name){
        publisherRepository.deleteByName(name);
    }
}
