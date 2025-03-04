package com.laur.bookshop.controller;

import com.laur.bookshop.model.data.Publisher;
import com.laur.bookshop.services.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) { this.publisherService = publisherService; }

    @GetMapping("/publishers")
    public List<Publisher> getAllPublishers() {
        return publisherService.findAllPublishers();
    }

    @GetMapping("/publishers/{name}")
    public Publisher getPublisherByName(@PathVariable String name) {
        return publisherService.findPublisherByName(name);
    }

    @GetMapping("/publishers/{location}")
    public List<Publisher> getPublisherByLocation(@PathVariable String location) {
        return publisherService.findPublisherByLocation(location);
    }

    @GetMapping("/publishers/{foundingYear}")
    public List<Publisher> getPublisherByFoundingYear(@PathVariable int foundingYear) {
        return publisherService.findPublisherByFoundingYear(foundingYear);
    }

    @PutMapping("/publishers/{title}")
    public Publisher updatePublisher(@PathVariable String title, @RequestBody Publisher publisher) {
        return publisherService.updatePublisher(title, publisher);
    }

    @DeleteMapping("/publishers/{name}")
    public void deletePublisherByName(@PathVariable String name) {
        publisherService.deletePublisherByName(name);
    }

    @PostMapping("/publishers")
    public Publisher addPublisher(@RequestBody Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }
}
