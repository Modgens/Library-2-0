package com.example.library20.service;
import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.entity.Genre;
import com.example.library20.entity.Publisher;
import com.example.library20.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ImageService imageService;

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

    public Page<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    public Page<Book> getAllByTitle( String title, Pageable pageable) {
        return bookRepository.findAllByTitleIsContaining(title, pageable);
    }

    public boolean delete(Long id) {
        try {
            imageService.deleteImg(bookRepository.findById(id).get().getImageName());
            bookRepository.deleteById(id);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public Book getById(Long id) {
        var book = bookRepository.findById(id);
        if(book.isPresent())
            return book.get();
        throw new NullPointerException();
    }

    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        bookRepository.save(book.increaseAmount());
    }

    public void giveBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        bookRepository.save(book.reduceAmount());
    }

    public void save(Long id, String title, Integer amount, Integer year, String description, Long[] genres, Long[] authors, Long publisher_id, MultipartFile file) {

        String imgName = "default.jpg";

        if(!file.isEmpty()) {
            imgName = imageService.saveImg(file);
        }

        List<Genre> genreList = new ArrayList<>();
        for (Long l : genres) {
            genreList.add(new Genre(l));
        }
        List<Author> authorList = new ArrayList<>();
        for (Long l : authors) {
            authorList.add(new Author(l));
        }
        Book book = Book.builder()
                .title(title)
                .amount(amount)
                .year(year)
                .description(description)
                .genres(genreList)
                .authors(authorList)
                .imageName(imgName)
                .publisher(new Publisher(publisher_id))
                .build();
        if(id!=null){
            book.setId(id);
            imageService.deleteImg(bookRepository.findById(id).get().getImageName());
        }
        bookRepository.save(book);
    }

}
