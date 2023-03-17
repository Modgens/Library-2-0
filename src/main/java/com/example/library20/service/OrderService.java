package com.example.library20.service;

import com.example.library20.entity.Book;
import com.example.library20.entity.Enums.Status;
import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import com.example.library20.entity.User;
import com.example.library20.entity.dto.BookResponse;
import com.example.library20.repositories.BookRepository;
import com.example.library20.repositories.OrderRepository;
import com.example.library20.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;

import static com.example.library20.entity.Enums.Status.ORDERED;
import static com.example.library20.entity.Enums.Status.WAIT;

@Service
@AllArgsConstructor
public class OrderService {
    BookRepository bookRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public String createOrder(Long bookId, Long userId) {
        if(orderRepository.existsById_BookIdAndId_UserId(bookId, userId))
            return "You have ordered book already.";

        Book book = bookRepository.findById(bookId).orElseThrow();

        if(book.getAmount()<=0)
            return "The book is not available.";

        OrderKey key = new OrderKey();
        key.setUserId(userId);
        key.setBookId(bookId);

        orderRepository.save(
                Order.builder()
                        .id(key)
                        .user(userRepository.findById(userId).orElseThrow())
                        .book(book)
                        .status(WAIT)
                        .build()
        );
        return "";
    }
    public Page<Order> getAllByUserId(Pageable pageable, long userId) {
        return orderRepository.findAllById_UserId(userId, pageable);
    }

    @Transactional
    public void deleteByUserIdAndBookId(Long userId, Long bookId){
        orderRepository.deleteById_UserIdAndId_BookId(userId, bookId);
    }


    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public void saveOrder(Long bookId, Long userId, String dateToReturn) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = new Date(sdf.parse(dateToReturn).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Book book = bookRepository.findById(bookId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        OrderKey key = new OrderKey();
        key.setUserId(userId);
        key.setBookId(bookId);

        Order order = Order.builder()
                .id(key)
                .book(book)
                .user(user)
                .start_date(new java.sql.Date(Calendar.getInstance().getTimeInMillis()))
                .end_date(date)
                .status(ORDERED)
                .build();
        orderRepository.save(order);
    }

    public Page<Order> getAllByLoginOrName(String message, Pageable pageable) {
        return orderRepository.findOrdersByUserAttributes(message, pageable);
    }
}
