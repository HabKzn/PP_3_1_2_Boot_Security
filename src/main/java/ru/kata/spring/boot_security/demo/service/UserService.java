package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    void delete(int id);

    void save(User user);

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

}
