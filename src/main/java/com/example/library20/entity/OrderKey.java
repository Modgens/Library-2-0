package com.example.library20.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class OrderKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "book_id")
    private Long bookId;

}
