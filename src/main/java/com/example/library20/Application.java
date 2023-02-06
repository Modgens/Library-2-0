package com.example.library20;

import com.example.library20.entity.Enums.Role;
import com.example.library20.entity.User;
import com.example.library20.repositories.BookRepository;
import com.example.library20.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.library20.entity.Enums.Role.USER;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
