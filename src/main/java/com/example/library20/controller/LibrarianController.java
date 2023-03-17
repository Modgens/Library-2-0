package com.example.library20.controller;

import com.example.library20.auth.AuthenticationService;
import com.example.library20.auth.RegisterRequest;
import com.example.library20.entity.Author;
import com.example.library20.entity.Enums.Role;
import com.example.library20.entity.User;
import com.example.library20.service.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.library20.entity.Enums.Role.LIBRARIAN;
import static org.springframework.data.domain.Sort.Direction.ASC;


@Controller
@RequiredArgsConstructor
@RequestMapping("/librarians")
public class LibrarianController {
    private final LibrarianService librarianService;
    private final AuthenticationService authenticationService;

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        librarianService.delete(id);
        return "redirect:/librarians/all";
    }
    @GetMapping("/createForm")
    public String createLibrarian(Model model) {
        model.addAttribute("url", "/librarians/createForm");
        return "/html/librarianEditor";
    }
    @PostMapping("/save")
    public String create(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password
    ) {
        try {
            authenticationService.register(new RegisterRequest(firstName, lastName, login, password, LIBRARIAN));
        } catch (Exception ex) {
            return "redirect:/librarians/createForm?failed=A User with such a login already exists.";
        }
        return "redirect:/librarians/createForm?success";
    }

    @GetMapping("/all")
    public String allAuthors(Model model, @PageableDefault(sort = {"id"}, direction = ASC, size = 15) Pageable pageable, @RequestParam(required = false, defaultValue = "") String login) {
        Page<User> page;

        if(!login.equals("")){
            page = librarianService.getAllLibrarianByLogin(login, pageable);
        } else {
            page = librarianService.getAllLibrarian(pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("url", "/librarians/all");
        return "/html/librarians";
    }
}
