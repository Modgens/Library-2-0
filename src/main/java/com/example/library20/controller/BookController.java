package com.example.library20.controller;

import com.example.library20.entity.dto.BookResponse;
import com.example.library20.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books")
    public String allBooks() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String allBooks(
            Model model,
            @PageableDefault(sort = {"id"}, direction = DESC, size = 20) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String title
    ) {

        Page<BookResponse> page;
        if(title != null && !title.isEmpty()){
            page = bookService.getAllByTitle(title, pageable);
        } else {
            page = bookService.getAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/books");
        model.addAttribute("status", "");
        return "/html/books";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("url", "/books/" + id);
        return "/html/book";
    }
}
