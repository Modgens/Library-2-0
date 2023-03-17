package com.example.library20.auth;

import com.example.library20.entity.Enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.library20.entity.Enums.Role.USER;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/register")
    public String registrationPage() {
        return "html/registration";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password
    ) {
        try {
            service.register(new RegisterRequest(firstName, lastName, login, password, USER));
        } catch (Exception ex) {
            return "redirect:/register?failed=User with this login already exists";
        }
        return "redirect:/register?success";
    }
}
