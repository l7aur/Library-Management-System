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

    public List<Publisher> getAllPublishers(){
        return publisherRepository.findAll();
    }

    public Publisher savePublisher(Publisher publisher){
        return publisherRepository.save(publisher);
    }
}
