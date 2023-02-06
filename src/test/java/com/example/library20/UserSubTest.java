package com.example.library20;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.library20.entity.Subscription;
import com.example.library20.entity.User;
import com.example.library20.repositories.SubscriptionRepository;
import com.example.library20.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.sql.Time;

import static com.example.library20.entity.Enums.Role.USER;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserSubTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;

    final Faker faker = new Faker();

    public User getFakeUser(){
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.gameOfThrones().dragon(),
                "1234567123",
                USER
        );
    }
    public Subscription getFakeSub(){
        return new Subscription(
                new Date(new java.util.Date().getTime())
        );
    }

    @Test
    public void newUserWithoutSubTest(){
        User user = userRepository.save(getFakeUser());
        assertThat(user.getId()).isGreaterThan(0);
        User resUser = userRepository.findById(user.getId()).get();
        assertThat(user).isEqualTo(resUser);
    }
    @Test void SubForUserAndUpdateUserTest(){
        Subscription subscription = subscriptionRepository.save(getFakeSub());
        User user = getFakeUser();
        user.setSubscription(subscription);
        user = userRepository.save(user);

        assertThat(subscriptionRepository.findById(subscription.getId()).get().getUser()).isEqualTo(user);

        user.setLogin("someLogin");
        user = userRepository.save(user);

        assertThat(subscriptionRepository.findById(subscription.getId()).get().getUser()).isEqualTo(user);
    }
    @Test void UserDeleteTest(){
        Subscription subscription = subscriptionRepository.save(getFakeSub());
        User user = getFakeUser();
        user.setSubscription(subscription);
        user = userRepository.save(user);

        userRepository.delete(user);

        var users = userRepository.findAll();
        var subs = subscriptionRepository.findAll();

        assertThat(users).isEmpty();
        assertThat(subs).isEmpty();

    }
}
