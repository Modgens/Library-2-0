package com.example.library20.entity;

import com.example.library20.entity.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Order {
    @EmbeddedId
    private OrderKey id;

    private Date start_date;
    private Date end_date;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public Order(Date start_date, Date end_date, Status status, User user, Book book) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.user = user;
        this.book = book;
    }
}
