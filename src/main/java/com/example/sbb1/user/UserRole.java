package com.example.sbb1.user;

import lombok.Getter;

@Getter
//enum(열거 자료형)
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}