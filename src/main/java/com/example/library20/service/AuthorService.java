package com.example.library20.service;

import com.example.library20.entity.Author;
import com.example.library20.entity.Book;
import com.example.library20.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    public Page<Author> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Page<Author> getAllByName(String search, Pageable pageable) {
        return authorRepository.findAllByName(search, pageable);
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        List<Book> books = author.getBooks();
        for (Book book : books) {
            book.getAuthors().remove(author);
        }
        authorRepository.delete(author);
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
