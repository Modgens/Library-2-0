package com.example.library20.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genres")
@Builder
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
