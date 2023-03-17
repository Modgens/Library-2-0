package com.example.library20.repositories;

import com.example.library20.entity.Author;
import com.example.library20.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    void deleteById(Long id);
    Page<Publisher> findAll(Pageable pageable);

    List<Publisher> findAll();

    Page<Publisher> findAllByName(String search, Pageable pageable);
}
