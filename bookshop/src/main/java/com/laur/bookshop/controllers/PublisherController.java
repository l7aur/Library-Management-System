package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.PublisherDTO;
import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.services.PublisherService;
import jakarta.annotation.security.PermitAll;
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

    @PermitAll
    @GetMapping("/publishers/all")
    public List<Publisher> getAllPublishers() {
        return service.findAllPublishers();
    }

    @PermitAll
    @PostMapping("/publishers/add")
    public Publisher addPublisher(@Valid @RequestBody PublisherDTO validatedPublisher) {
        return service.addPublisher(validatedPublisher);
    }

    @PermitAll
    @DeleteMapping("/publishers/delete")
    public ResponseEntity<String> deletePublisher(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return service.deleteByIds(idList);
    }

    @PermitAll
    @PutMapping("/publishers/edit")
    public Publisher updatePublisher(@Valid @RequestBody PublisherDTO publisher) {
        return service.updatePublisher(publisher);
    }

    @GetMapping("/publishers/{name}")
    public Publisher getPublisherByName(@PathVariable String name) {
        return service.findPublisherByName(name);
    }
}
