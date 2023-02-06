package com.example.library20.controller;

import com.example.library20.dto.BookDTO;
import com.example.library20.dto.UserDTO;
import com.example.library20.entity.Book;
import com.example.library20.entity.User;
import com.example.library20.service.BookService;
import com.example.library20.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
//    public ResponseEntity<User> create(@RequestBody UserDTO dto) {
//        return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> readById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }
}