package com.laur.bookshop.services;

import com.laur.bookshop.model.data.Publisher;
import com.laur.bookshop.repository.data.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAllPublishers(){
        return publisherRepository.findAll();
    }

    public Publisher findPublisherByName(String name){
        return publisherRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("No publisher found with name " + name)
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

    public Publisher addPublisher(Publisher publisher){
        return publisherRepository.save(publisher);
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
