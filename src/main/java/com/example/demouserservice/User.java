package com.example.demouserservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id
        Long id,
        String username,
        String firstName,
        String lastName,
        int rating,
        String phoneNumber
) {
    public User of (String username, String firstName, String lastName, int rating, String phoneNumber) {
        return new User(null, username, firstName, lastName, rating, phoneNumber);
    }
}
