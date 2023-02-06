package com.example.library20.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Builder
@Data
@ToString(exclude = "publisher")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Integer year;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany
    private Set<Genre> genres;

    @ManyToMany
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private Set<Order> orders;
}
