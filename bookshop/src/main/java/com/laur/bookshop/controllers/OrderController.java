package com.laur.bookshop.controllers;

import com.laur.bookshop.config.dto.BookOrderDTO;
import com.laur.bookshop.config.dto.ConfirmationEmailDTO;
import com.laur.bookshop.config.dto.ConfirmationEmailData;
import com.laur.bookshop.model.BookOrder;
import com.laur.bookshop.services.BookService;
import com.laur.bookshop.services.EmailService;
import com.laur.bookshop.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class OrderController {
    private final OrderService service;
    private final EmailService emailService;
    private final BookService bookService;

    @PostMapping("/orders/place")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid BookOrderDTO dto) {
        return (null != service.saveOrderedBook(dto))
                ? ResponseEntity.status(200).body("ok")
                : ResponseEntity.status(501).body("error");
    }

    @GetMapping("orders/orderNumber")
    public List<BookOrder> getOrderByOrderNumber(@RequestParam Integer orderNumber) {
        return service.getOrderedBooksEntries(orderNumber);
    }

//    @GetMapping("orders/appUserId")
//    public List<List<Book>> getAllOrdersByAppUser(@RequestParam UUID appUserId) {
//        List<List<Optional<Book>>> books = service.getOrderedBooks(appUserId);
//        return books.stream()
//                .map(innerList -> innerList
//                        .stream()
//                        .filter(Optional::isPresent)
//                        .map(Optional::get)
//                        .toList())
//                .toList();
//    }

    @GetMapping("orders/get_order_number")
    public Integer generateOrderNumber() {
        return service.generateNewOrder();
    }

    @PostMapping("orders/send_email")
    public ResponseEntity<String> sendEmail(@RequestBody ConfirmationEmailDTO ce) {
        List<ConfirmationEmailData> items = service.process(ce);
        return emailService.sendConfirmationEmail(ce.getTo(), ce.getOrderNumber(), items)
                ? ResponseEntity.status(200).body("ok")
                : ResponseEntity.status(501).body("error");
    }

}
