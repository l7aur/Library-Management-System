package com.laur.bookshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.laur.bookshop.config.dto.PublisherDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "publisher")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "founding_year")
    private Integer foundingYear;

    @OneToMany(
            mappedBy = "publisher",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books;

    public PublisherDTO toDTO() {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setLocation(this.location);
        publisherDTO.setName(this.name);
        publisherDTO.setFoundingYear(this.foundingYear);
        publisherDTO.setBooks(this.books.stream().map(Book::toString).toList());
        return publisherDTO;
    }
}
