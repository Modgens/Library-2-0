package com.example.library20;

import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.entity.Genre;
import com.example.library20.entity.Publisher;
import com.example.library20.repositories.AuthorRepository;
import com.example.library20.repositories.BookRepository;
import com.example.library20.repositories.GenreRepository;
import com.example.library20.repositories.PublisherRepository;
import com.example.library20.service.BookService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository, GenreRepository genreRepository){
        return args -> {
            Faker faker = new Faker();
            for (int i = 0; i < 4; i++) {
                List<Author> authors = new ArrayList<>();
                authors.add(new Author(faker.name().firstName(), faker.name().lastName()));
                authors.add(new Author(faker.name().firstName(), faker.name().lastName()));
                authorRepository.saveAll(authors);

                List<Genre> genres = new ArrayList<>();
                genres.add(new Genre(faker.book().genre()));
                genres.add(new Genre(faker.book().genre()));
                genreRepository.saveAll(genres);

                Publisher publisher = new Publisher(faker.rockBand().name());
                publisherRepository.save(publisher);

                bookRepository.save(
                        Book.builder()
                                .title(faker.book().title())
                                .publisher(publisher)
                                .year(faker.date().birthday().getYear()+1900)
                                .amount(faker.number().randomDigit())
                                .authors(authors)
                                .genres(genres)
                                .imageName("default.jpg")
                                .description(String.join("", faker.lorem().paragraphs(20)))
                                .build()
            );
            }
        };
    }
}
