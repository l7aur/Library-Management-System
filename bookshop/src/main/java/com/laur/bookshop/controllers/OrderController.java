package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.BookOrderDTO;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.BookOrder;
import com.laur.bookshop.services.AppUserService;
import com.laur.bookshop.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class OrderController {
    private final OrderService service;
    private final AppUserService userService;

    @PostMapping("/orders/place")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid BookOrderDTO dto) {
        return (null != service.saveOrderedBook(dto))
                ? ResponseEntity.status(200).body("ok")
                : ResponseEntity.status(501).body("error");
    }

    @GetMapping("orders/orderNumber")
    public List<Book> getOrderById(@RequestParam Integer orderNumber) {
        List<Optional<Book>> books = service.getOrderedBooks(orderNumber);
        return books.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @GetMapping("orders/appUserId")
    public List<List<Book>> getAllOrdersByAppUser(@RequestParam UUID appUserId) {
        List<List<Optional<Book>>> books = service.getAllOrders(appUserId);
        return books.stream()
                .map(innerList -> innerList
                        .stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .toList();
    }
}
