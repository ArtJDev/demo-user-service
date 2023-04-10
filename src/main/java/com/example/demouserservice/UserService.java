package com.example.demouserservice;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> saveUser(UserDto userDto) {
        return userRepository.findByUsername(userDto.username())
                .map(createdUser -> new User(null,
                        userDto.username(),
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.rating(),
                        userDto.phoneNumber()))
                .flatMap(userRepository::save)
                .doOnError(error -> {
                    throw new RuntimeException("Пользователь с таким именем уже существует");
                });
    }

    public Mono<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(updatedUser -> new User(
                        user.id(),
                        user.username(),
                        user.firstName(),
                        user.lastName(),
                        user.rating(),
                        user.phoneNumber()))
                .flatMap(userRepository::save)
                .doOnError(error -> {
                    throw new RuntimeException("Имя пользователя изменять нельзя!");
                });
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}