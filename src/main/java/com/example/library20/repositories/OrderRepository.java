package com.example.library20.repositories;

import com.example.library20.entity.Order;
import com.example.library20.entity.OrderKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsById_BookIdAndId_UserId(Long bookId, Long UserId);
    Page<Order> findAllById_UserId(long userId, Pageable pageable);
    void deleteById_UserIdAndId_BookId(Long userId, Long bookId);
    @Query("SELECT o FROM Order o WHERE o.user.login = :message OR o.user.firstName = :message OR o.user.lastName = :message")
    Page<Order> findOrdersByUserAttributes(String message, Pageable pageable);
}
