package com.laur.bookshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.laur.bookshop.config.dto.BookOrderDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "book_order")
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "app_user_id",
            nullable = false
    )
    @JsonBackReference
    private AppUser appUser;

    @Column
    private Integer orderNumber;

    @Column
    private Date orderDate;

    @Column
    private UUID bookID;

    @Column
    private Double price;

    @Column
    private Integer quantity;

    BookOrderDTO toDTO() {
        BookOrderDTO dto = new BookOrderDTO();
        dto.setUsername(appUser.getUsername());
        dto.setBookId(bookID.toString());
        dto.setOrderNumber(orderNumber);
        dto.setOrderDate(orderDate);
        dto.setPrice(price);
        dto.setQuantity(quantity);
        return dto;
    }
}
