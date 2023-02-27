package com.example.library20.service;
import com.example.library20.entity.Book;
import com.example.library20.entity.dto.BookResponse;
import com.example.library20.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book create(Book book) {
        return bookRepository.save(Book.builder()
                .title(book.getTitle())
                .amount(book.getAmount())
                .description(book.getDescription())
                .year(book.getYear())
                .publisher(book.getPublisher())
                .genres(book.getGenres())
                .authors(book.getAuthors())
                .build());
    }

    public Page<BookResponse> getAll(Pageable pageable) {
        return DtoManager.init().listOfBookToResponseBook(bookRepository.findAll(pageable));
    }
    public Page<BookResponse> getAllByTitle( String title, Pageable pageable) {
        return DtoManager.init().listOfBookToResponseBook(bookRepository.findAllByTitleIsContaining(title, pageable));
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookResponse getById(Long id) {
        var book = bookRepository.findById(id);
        if(book.isPresent())
            return DtoManager.init().bookToBookResponse(book.get());
        throw new NullPointerException();
    }
}
