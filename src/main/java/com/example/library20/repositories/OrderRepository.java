package com.example.library20.repositories;

import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsById_BookIdAndId_UserId(Long bookId, Long UserId);
    Page<Order> findAllById_UserId(long userId, Pageable pageable);
    void deleteById_UserIdAndId_BookId(Long userId, Long bookId);
}
