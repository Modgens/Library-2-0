package com.example.library20.controller;

import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.entity.Genre;
import com.example.library20.entity.Publisher;
import com.example.library20.repositories.GenreRepository;
import com.example.library20.service.AuthorService;
import com.example.library20.service.BookService;
import com.example.library20.service.PublisherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final GenreRepository genreRepository;

    @GetMapping("/public/all")
    public String showAllBooks(
            Model model,
            @PageableDefault(sort = {"id"}, direction = DESC, size = 20) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String title,
            HttpServletRequest request
    ) {
        Page<Book> page = getBookPage(title, pageable);
        addFlashAttributesToModel(model, request);
        model.addAttribute("page", page);
        model.addAttribute("url", "/books/public/all");
        return "/html/books";
    }

    @GetMapping("/public/{id}")
    public String getBook(@PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("url", "/books/public/" + id);
        model.addAttribute("backUrl", request.getHeader("Referer"));
        addFlashAttributesToModel(model, request);
        return "/html/book";
    }
    @GetMapping("/newBookForm/{id}")
    public String createForm(Model model, @PathVariable(required = false) Long id) {
        List<Author> authors = authorService.getAll();
        authors.sort(Comparator.comparing(Author::getFirstName));

        List<Publisher> publishers = publisherService.getAll();
        publishers.sort(Comparator.comparing(Publisher::getName));

        List<Genre> genres = genreRepository.findAll();
        genres.sort(Comparator.comparing(Genre::getName));

        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        model.addAttribute("genres", genres);

        if(id!=-1) {
            model.addAttribute("book", bookService.getById(id));
        }
        return "/html/book_form";
    }
    @PostMapping("saveBook")
    public String save(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam Integer amount,
            @RequestParam Integer year,
            @RequestParam String description,
            @RequestParam Long[] genres,
            @RequestParam Long[] authors,
            @RequestParam Long publisher,
            @RequestParam(value = "file") MultipartFile file
    ){
        bookService.save(id, title, amount, year, description, genres, authors, publisher, file);
        return "redirect:/books/table";
    }

    @GetMapping("/table")
    public String tableOfBooks(
            Model model,
            @PageableDefault(sort = {"id"}, direction = DESC, size = 20) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String title
    ){
        Page<Book> page = getBookPage(title, pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/books/table");
        return "html/table";
    }
    @GetMapping("/bookDelete/{id}")
    public String deleteBook(@PathVariable Long id, HttpServletRequest request){
        if(!bookService.delete(id)){
            return "redirect:" + request.getHeader("Referer") + "?status=failed";
        }
        return "redirect:" + request.getHeader("Referer");
    }

    private Page<Book> getBookPage(String title, Pageable pageable) {
        return (title != null && !title.isEmpty()) ? bookService.getAllByTitle(title, pageable) : bookService.getAll(pageable);
    }

    private void addFlashAttributesToModel(Model model, HttpServletRequest request) {
        Map<String, ?> flashAttributes = RequestContextUtils.getInputFlashMap(request);
        String status = "";
        String error = "";
        if (flashAttributes != null) {
            status = (String) flashAttributes.get("status");
            error = (String) flashAttributes.get("errorMessage");
        }
        model.addAttribute("status", status);
        model.addAttribute("errorMessage", error);
    }
}

