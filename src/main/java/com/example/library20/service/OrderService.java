package com.example.library20.service;

import com.example.library20.entity.Book;
import com.example.library20.entity.Enums.Status;
import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import com.example.library20.entity.dto.BookResponse;
import com.example.library20.repositories.BookRepository;
import com.example.library20.repositories.OrderRepository;
import com.example.library20.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

import static com.example.library20.entity.Enums.Status.WAIT;

@Service
@AllArgsConstructor
public class OrderService {
    BookRepository bookRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public boolean createOrder(Long bookId, Long userId) throws NoSuchElementException {
        if(orderRepository.existsById_BookIdAndId_UserId(bookId, userId))
            return false;

        Book book = bookRepository.findById(bookId).orElseThrow();

        if(book.getAmount()<=0)
            return false;

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
        return true;
    }
    public Page<Order> getAllByUserId(Pageable pageable, long userId) {
        return orderRepository.findAllById_UserId(userId, pageable);
    }

    @Transactional
    public void deleteByUserIdAndBookId(Long userId, Long bookId){
        orderRepository.deleteById_UserIdAndId_BookId(userId, bookId);
    }


}
