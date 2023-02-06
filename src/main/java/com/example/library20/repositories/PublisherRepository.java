package com.example.library20.repositories;

import com.example.library20.entity.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    public void deleteById(Long id);
}
