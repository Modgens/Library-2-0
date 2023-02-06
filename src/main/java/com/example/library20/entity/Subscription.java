package com.example.library20.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Data
@ToString(exclude = "user")
@EqualsAndHashCode
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "subscription")
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sub_date;

    @PrePersist
    protected void onCreate(){
        this.sub_date = LocalDateTime.now();
    }

}
