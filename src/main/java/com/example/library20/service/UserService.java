package com.example.library20.service;

import com.example.library20.dto.BookDTO;
import com.example.library20.dto.UserDTO;
import com.example.library20.entity.Book;
import com.example.library20.entity.Enums.Role;
import com.example.library20.entity.User;
import com.example.library20.repositories.BookRepository;
import com.example.library20.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.library20.entity.Enums.Role.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    public User create(UserDTO dto) {
//        return userRepository.save(
//                User.builder()
//                        .login(dto.getLogin())
//                        .password(dto.getPassword())
//                        .role(dto.getRole())
//                        .firstName(dto.getFirstName())
//                        .lastName(dto.getLastName())
//                        .build()
//        );
//    }

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
