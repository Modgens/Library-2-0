package com.example.library20.dto;

import com.example.library20.entity.Author;
import com.example.library20.entity.Genre;
import com.example.library20.entity.Publisher;
import lombok.Data;

import java.util.Set;

@Data
public class BookDTO {
    private String title;

    private Integer year;

    private String description;

    private Integer amount;

    private Publisher publisher;

    private Set<Genre> genres;

    private Set<Author> authors;
}
