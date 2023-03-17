package com.example.library20.repositories;

import com.example.library20.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.firstName = :message OR a.lastName = :message")
    Page<Author> findAllByName(String message, Pageable pageable);
}
