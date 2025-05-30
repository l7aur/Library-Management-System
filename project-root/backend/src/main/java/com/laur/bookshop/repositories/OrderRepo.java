package com.laur.bookshop.repositories;

import com.laur.bookshop.model.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<BookOrder, UUID> {
    List<BookOrder> findByOrderNumber(Integer orderNumber);
    List<BookOrder> findBookOrdersByAppUserId(UUID appUserId);
}
