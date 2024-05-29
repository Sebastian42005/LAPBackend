package org.example.buchibackend.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role fromString(String name) {
        return Arrays.stream(Role.values()).filter(role -> role.name.equalsIgnoreCase(name))
                .findFirst().orElseThrow();
    }
}

