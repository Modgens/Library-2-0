package com.example.library20.dto;

import com.example.library20.entity.Enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private Role role;
}
