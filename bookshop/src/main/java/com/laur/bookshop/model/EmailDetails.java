package com.laur.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "mails")
public class EmailDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    @Column(name = "expiration_time", nullable = false)
    private LocalTime expirationTime;

    @Column(name = "code", nullable = false)
    private String code;
}
