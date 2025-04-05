package com.laur.bookshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.laur.bookshop.config.dto.AuthorDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "author")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "alias")
    private String alias;

    @Column(name = "nationality")
    private String nationality;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public AuthorDTO toDTO() {
        AuthorDTO dto = new AuthorDTO();
        dto.setAlias(this.alias);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setNationality(this.nationality);
        dto.setBooks(Collections.emptyList());
        return dto;
    }
}
