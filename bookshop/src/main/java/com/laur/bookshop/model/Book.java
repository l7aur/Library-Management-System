package com.laur.bookshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.laur.bookshop.config.dto.BookDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany()
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @Column(name = "publish_year")
    private Integer publishYear;

    @ManyToOne
    @JoinColumn(
            name = "publisher_id",
            nullable = false
    )
    private Publisher publisher;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    public BookDTO toDTO() {
        BookDTO dto = new BookDTO();
        dto.setIsbn(this.isbn);
        dto.setTitle(this.title);
        dto.setStock(this.stock);
        dto.setPublishYear(this.publishYear);
        dto.setPrice(this.price);
        dto.setAuthorIds(this.authors.stream().map(Author::getId).toList());
        dto.setPublisherId(this.publisher.getId());
        return dto;
    }
}
