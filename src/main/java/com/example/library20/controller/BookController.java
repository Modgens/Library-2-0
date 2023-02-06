package com.example.library20.controller;

import com.example.library20.dto.BookDTO;
import com.example.library20.entity.Book;
import com.example.library20.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody BookDTO dto) {
        return new ResponseEntity<>(bookService.create(dto), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Book>> readAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> readById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }
}
