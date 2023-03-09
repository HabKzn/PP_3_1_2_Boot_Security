package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping()
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.listRoles(), HttpStatus.OK);
    }


    @PatchMapping("/update")
    public User updateUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @GetMapping("/userView/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь с таким id не найден")), HttpStatus.OK);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }


    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }
}