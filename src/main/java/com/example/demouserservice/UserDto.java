package com.example.demouserservice;

public record UserDto(
        String firstName,
        String lastName,
        int rating,
        String phoneNumber
) {
}
