package com.laur.bookshop.controllers;

import com.laur.bookshop.config.validators.model.PublisherValidator;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.services.PublisherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class PublisherController {
    final PublisherService service;

    @GetMapping("/publishers/all")
    public List<Publisher> getAllPublishers() {
        return service.findAllPublishers();
    }

    @PostMapping("/publishers/add")
    public Publisher addPublisher(@Valid @RequestBody PublisherValidator validatedPublisher) {
        return service.addPublisher(validatedPublisher);
    }

    @DeleteMapping("/publishers/delete")
    public ResponseEntity<String> deletePublisher(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return service.deleteByIds(idList);
    }

    @PutMapping("/publishers/edit")
    public Publisher updatePublisher(@RequestBody Publisher publisher) {
        return service.updatePublisher(publisher);
    }

    @GetMapping("/publishers/{name}")
    public Publisher getPublisherByName(@PathVariable String name) {
        return service.findPublisherByName(name);
    }
}
