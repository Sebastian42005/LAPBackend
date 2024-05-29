package org.example.buchibackend.dto;

import org.example.buchibackend.domain.User;
import org.example.buchibackend.enums.Role;

import java.util.Date;

public record RegisterRequest(String email, String firstname, String lastname, Date birthdate, String password, String role) {

    public User getUserEntity() {
        return User.builder()
                .email(email)
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .birthdate(birthdate)
                .role(Role.fromString(role))
                .build();
    }
}
