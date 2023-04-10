package com.example.demouserservice;

public record UserDto(
        String username,
        String firstName,
        String lastName,
        int rating,
        String phoneNumber
) {
}
