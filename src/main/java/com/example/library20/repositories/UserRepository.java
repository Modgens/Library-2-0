package com.example.library20.repositories;

import com.example.library20.entity.Enums.Role;
import com.example.library20.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Page<User> findAllByRole(Role role, Pageable pageable);
    Page<User> findAllByRoleAndLogin(Role role, String login, Pageable pageable);
    User findByIdAndRole(Long id, Role librarian);
}
