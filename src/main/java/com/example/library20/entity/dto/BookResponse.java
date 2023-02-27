package com.example.library20.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class BookResponse {

    private Long id;

    private String title;

    private Integer year;

    private String description;

    private String publisher;

    private String genres;

    private String authors;

    private String imgName;

    private Integer amount;
}
