package com.laur.bookshop.controller;

import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.model.PublisherCreateDTO;
import com.laur.bookshop.services.PublisherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public List<Publisher> getAllPublishers() {
        return publisherService.findAllPublishers();
    }

    @GetMapping("/publishers/{name}")
    public Publisher getPublisherByName(@PathVariable String name) {
        return publisherService.findPublisherByName(name);
    }

    @GetMapping("/publishers/location/{location}")
    public List<Publisher> getPublisherByLocation(@PathVariable String location) {
        return publisherService.findPublisherByLocation(location);
    }

    @GetMapping("/publishers/foundingYear/{foundingYear}")
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

    @PostMapping("/publishers/add")
    public Publisher addPublisher(@Valid @RequestBody PublisherCreateDTO publisher) {
        return publisherService.addPublisher(publisher);
    }
}
