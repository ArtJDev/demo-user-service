package com.example.demouserservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("users")
public record User(
        @Id
        Long id,
        String username,
        String firstName,
        String lastName,
        int rating,
        String phoneNumber,
        @MappedCollection(idColumn = "id")
        List<String> roles
) {
}