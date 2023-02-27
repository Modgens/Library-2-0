package com.example.library20.service;

import com.example.library20.entity.Book;
import com.example.library20.entity.Genre;
import com.example.library20.entity.dto.BookResponse;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class DtoManager {
    private static DtoManager init;

    public static DtoManager init() {
        if (init==null)
            init = new DtoManager();
        return init;
    }

    public BookResponse bookToBookResponse (Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genres(book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")))
                .year(book.getYear())
                .description(book.getDescription())
                .publisher(book.getPublisher().getName())
                .authors(book.getAuthors().stream().map(author -> author.getFirstName() + " " + author.getLastName()).collect(Collectors.joining(", ")))
                .imgName(book.getImageName())
                .amount(book.getAmount())
                .build();
    }
    public Page<BookResponse> listOfBookToResponseBook (Page<Book> books) {
        return books.map(this::bookToBookResponse);
    }

}
