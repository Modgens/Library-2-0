package com.example.library20.service;

import com.example.library20.entity.User;
import com.example.library20.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(
                User.builder()
                        .login(user.getLogin())
                        .password(user.getPassword())
                        .role(user.getRole())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build()
        );
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getById(Long id) {
        var user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        throw new NullPointerException();
    }
}
