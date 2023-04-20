package com.example.demouserservice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                        userDto.phoneNumber(),
                        null))
                .flatMap(userRepository::save)
                .doOnError(error -> {
                    throw new RuntimeException("Пользователь с таким именем уже существует");
                });
    }

    public Mono<ResponseEntity<User>> getUser(Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<User> getProfile(Jwt principal) {
        Map<String, Object> claims = principal.getClaims();
        User user = new User(null,
                claims.get("preferred_username").toString(),
                claims.get("given_name").toString(),
                claims.get("family_name").toString(),
                5,
                null,
                principal.getClaimAsStringList("roles"));
        return userRepository.save(user).onErrorReturn(user);

    }

    public Mono<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(updatedUser -> new User(
                        user.id(),
                        user.username(),
                        user.firstName(),
                        user.lastName(),
                        user.rating(),
                        user.phoneNumber(),
                        user.roles()))
                .flatMap(userRepository::save)
                .doOnError(error -> {
                    throw new RuntimeException("Имя пользователя изменять нельзя!");
                });
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}