package com.example.library20.service;

import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.entity.Publisher;
import com.example.library20.repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public Page<Publisher> getAll(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    public Page<Publisher> getAllByName(String search, Pageable pageable) {
        return publisherRepository.findAllByName(search, pageable);
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow();
    }

    public void save(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void deletePublisher(Long publisherId) throws SQLIntegrityConstraintViolationException{
        publisherRepository.deleteById(publisherId);
    }

    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }
}
