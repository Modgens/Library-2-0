package com.example.library20.service;

import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.entity.Enums.Role;
import com.example.library20.entity.Order;
import com.example.library20.entity.User;
import com.example.library20.repositories.OrderRepository;
import com.example.library20.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.library20.entity.Enums.Role.LIBRARIAN;

@Service
@AllArgsConstructor
public class LibrarianService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findByIdAndRole(id, LIBRARIAN);
        List<Order> orders = user.getOrders();
        orderRepository.deleteAll(orders);
        userRepository.deleteById(id);
    }

    public Page<User> getAllLibrarian(Pageable pageable) {
        return userRepository.findAllByRole(LIBRARIAN, pageable);
    }

    public Page<User> getAllLibrarianByLogin(String login, Pageable pageable) {
        return userRepository.findAllByRoleAndLogin( LIBRARIAN, login, pageable);
    }
}
