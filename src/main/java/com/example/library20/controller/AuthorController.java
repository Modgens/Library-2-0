package com.example.library20.controller;

import com.example.library20.entity.Author;
import com.example.library20.entity.Order;
import com.example.library20.entity.User;
import com.example.library20.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;


@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authorDelete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors/all";
    }
    @GetMapping("/authorEdit/{id}")
    public String editAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getById(id));
        model.addAttribute("url", "/authors/authorEdit");
        return "/html/authorEditor";
    }
    @GetMapping("/createAuthor")
    public String createAuthor(Model model) {
        model.addAttribute("url", "/authors/createAuthor");
        return "/html/authorEditor";
    }
    @PostMapping("/save")
    public String saveAuthor(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(required = false) Long id){
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        if(id!=null)
            author.setId(id);
        authorService.save(author);
        return "redirect:/authors/all";
    }

    @GetMapping("/all")
    public String allAuthors(Model model, @PageableDefault(sort = {"id"}, direction = ASC, size = 15) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search) {
        Page<Author> page = authorService.getAll(pageable);

        if(!search.equals(""))
            page = authorService.getAllByName(search, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/authors/all");
        return "/html/authors";
    }
}
