package com.example.library20.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "image_name")
    private String imageName;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<Order> orders;

    public Book increaseAmount(){
        this.amount++;
        return this;
    }
    public Book reduceAmount(){
        this.amount--;
        return this;
    }
}
