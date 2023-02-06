package com.example.library20.service;

import com.example.library20.dto.BookDTO;
import com.example.library20.entity.Book;
import com.example.library20.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book create(BookDTO dto) {
        return bookRepository.save(Book.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .year(dto.getYear())
                .publisher(dto.getPublisher())
                .genres(dto.getGenres())
                .authors(dto.getAuthors())
                .build());
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getById(Long id) {
        var book = bookRepository.findById(id);
        if(book.isPresent())
            return book.get();
        throw new NullPointerException();
    }
}
