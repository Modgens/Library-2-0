package com.example.library20.repositories;

import com.example.library20.entity.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    void deleteByUserId(Long user_id);
}
