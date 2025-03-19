package com.laur.bookshop.controller;

import com.laur.bookshop.model.Publisher;
import com.laur.bookshop.dto.PublisherCreateDTO;
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
    private final PublisherService publisherService;

    @GetMapping("/publishers/all")
    public List<Publisher> getAllPublishers() {
        return publisherService.findAllPublishers();
    }

    @PostMapping("/publishers/add")
    public Publisher addPublisher(@Valid @RequestBody PublisherCreateDTO publisher) {
        return publisherService.addPublisher(publisher);
    }

    @DeleteMapping("/publishers/delete")
    public ResponseEntity<String> deletePublisher(@RequestBody Map<String, List<UUID>> ids) {
        List<UUID> idList = ids.get("ids");
        return publisherService.deletePublishersById(idList);
    }

    @PutMapping("/publishers/edit")
    public Publisher updatePublisher(@RequestBody Publisher publisher) {
        return publisherService.updatePublisher(publisher);
    }

//    @GetMapping("/publishers/{name}")
//    public Publisher getPublisherByName(@PathVariable String name) {
//        return publisherService.findPublisherByName(name);
//    }

//    @GetMapping("/publishers/location/{location}")
//    public List<Publisher> getPublisherByLocation(@PathVariable String location) {
//        return publisherService.findPublisherByLocation(location);
//    }

//    @GetMapping("/publishers/foundingYear/{foundingYear}")
//    public List<Publisher> getPublisherByFoundingYear(@PathVariable int foundingYear) {
//        return publisherService.findPublisherByFoundingYear(foundingYear);
//    }

//    @DeleteMapping("/publishers/{name}")
//    public void deletePublisherByName(@PathVariable String name) {
//        publisherService.deletePublisherByName(name);
//    }
}
