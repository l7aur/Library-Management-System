package com.laur.bookshop.services;

import com.laur.bookshop.config.dto.BookOrderDTO;
import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.BookOrder;
import com.laur.bookshop.repositories.AppUserRepo;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo repo;
    private final BookRepo bookRepo;
    private final AppUserRepo userRepo;

    public BookOrder saveOrderedBook(BookOrderDTO dto) {
        Book book = bookRepo.findById(UUID.fromString(dto.getBookId()))
                .orElseThrow(() -> new BookNotFoundException("Ordered book UUID " + dto.getBookId() + " not found!"));
        AppUser user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new AppUserNotFoundException("AppUser username " + dto.getUsername() + " not found!"));
        BookOrder bookOrder= new BookOrder();
        bookOrder.setAppUser(user);
        bookOrder.setOrderDate(dto.getOrderDate());
        bookOrder.setPrice(dto.getPrice());
        bookOrder.setBookID(book.getId());
        bookOrder.setOrderNumber(dto.getOrderNumber());
        return repo.save(bookOrder);
    }

    public Double computeOrderPrice(Integer orderNumber) {
        Double price = 0.0;
        List<BookOrder> orderedItems = repo.findByOrderNumber(orderNumber);
        for(BookOrder item: orderedItems) {
            price += item.getPrice();
        }
        return  price;
    }

    public List<List<Optional<Book>>> getAllOrders(UUID userId) {
        List<List<Optional<Book>>> orders = new ArrayList<>();
        Set<Integer> associatedOrders = repo.findOrderNumbersByAppUserId(userId);
        for(Integer orderNumber : associatedOrders) {
            orders.add(getOrderedBooks(orderNumber));
        }
        return orders;
    }

    public List<Optional<Book>> getOrderedBooks(Integer orderNumber) {
        List<BookOrder> orders = repo.findByOrderNumber(orderNumber);
        List<Optional<Book>> books = new ArrayList<>();
        for(BookOrder order : orders) {
            books.add(bookRepo.findById(order.getId()));
        }
        return books;
    }
}
