package com.laur.bookshop.services;

import com.laur.bookshop.config.dto.BookOrderDTO;
import com.laur.bookshop.config.dto.ConfirmationEmailDTO;
import com.laur.bookshop.config.dto.ConfirmationEmailData;
import com.laur.bookshop.config.dto.FullOrderDTO;
import com.laur.bookshop.config.exceptions.AppUserNotFoundException;
import com.laur.bookshop.config.exceptions.BookNotFoundException;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.model.Book;
import com.laur.bookshop.model.BookOrder;
import com.laur.bookshop.repositories.AppUserRepo;
import com.laur.bookshop.repositories.BookRepo;
import com.laur.bookshop.repositories.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.laur.bookshop.config.enums.AppMessages.BOOK_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo repo;
    private final BookRepo bookRepo;
    private final AppUserRepo userRepo;
    private final AppUserRepo appUserRepo;

    @Transactional
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
        bookOrder.setQuantity(dto.getQuantity());
        return repo.save(bookOrder);
    }

    public List<BookOrder> getOrderedBooksEntries(Integer orderNumber) {
        return repo.findByOrderNumber(orderNumber);
    }

    public Integer generateNewOrder() {
        return repo.findAll()
                .stream()
                .map(BookOrder::getOrderNumber)
                .max(Integer::compareTo)
                .map(n -> n + 1)
                .orElse(1); // If no orders exist, start from 1
    }


    @Transactional
    public List<ConfirmationEmailData> process(ConfirmationEmailDTO ce) {
        List<ConfirmationEmailData> data = new ArrayList<>();
        List<BookOrder> bookOrders = repo.findByOrderNumber(ce.getOrderNumber());
        List<Pair<Book, Integer>> bookIds = new ArrayList<>();
        boolean missingItems = false;
        for(BookOrder bookOrder : bookOrders) {
            Book b = bookRepo.findById(bookOrder.getBookID())
                    .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE + bookOrder.getBookID().toString()));
            if (b.getStock() - bookOrder.getQuantity() >= 0) {
                bookIds.add(Pair.of(b, bookOrder.getQuantity()));
                data.add(new ConfirmationEmailData(b.getTitle(), bookOrder.getQuantity(), bookOrder.getPrice()));
            }
            else
                missingItems = true;
        }
        for(Pair<Book, Integer> b : bookIds) {
            b.getFirst().setStock(b.getFirst().getStock() - b.getSecond());
            bookRepo.save(b.getFirst());
        }
        if(missingItems)
            data.add(new ConfirmationEmailData(null, -1, 0.0));
        return data;
    }

    public Map<Integer, FullOrderDTO> getOrderHistory(String username) {
        UUID userId = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException("User " + username + " not found!"))
                .getId();

        List<BookOrder> bookOrders = repo.findBookOrdersByAppUserId(userId);
        if (bookOrders.isEmpty()) {
            return Collections.emptyMap();
        }

        Set<UUID> bookIds = bookOrders.stream()
                .map(BookOrder::getBookID)
                .collect(Collectors.toSet());

        Map<UUID, Book> booksById = bookRepo.findAllById(bookIds).stream()
                .collect(Collectors.toMap(Book::getId, book -> book));

        Map<Integer, FullOrderDTO> orders = new HashMap<>();

        for (BookOrder bookOrder : bookOrders) {
            Book book = booksById.get(bookOrder.getBookID());

            if (book == null) {
                System.err.println("Warning: Book with ID " + bookOrder.getBookID() +
                        " for Order Number " + bookOrder.getOrderNumber() + " not found. Skipping this item.");
                continue; // Skip processing this specific order item
            }

            ConfirmationEmailData item = new ConfirmationEmailData(
                    book.getTitle(),
                    bookOrder.getQuantity(),
                    bookOrder.getPrice()
            );

            orders.compute(bookOrder.getOrderNumber(), (orderNum, existingOrderDTO) -> {
                if (existingOrderDTO == null) {
                    return new FullOrderDTO(List.of(item), bookOrder.getOrderDate());
                } else {
                    List<ConfirmationEmailData> updatedItems = new ArrayList<>(existingOrderDTO.getItems());
                    updatedItems.add(item);
                    return new FullOrderDTO(updatedItems, existingOrderDTO.getDate());
                }
            });
        }

        return orders;
    }
}
