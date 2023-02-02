package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {

  private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @RequestMapping("/admin/adduser")
    public String addUser(User user, Model model) {
        userService.save(user);
        return "redirect:/admin/users-info";
    }

    @GetMapping("/admin/users-info")
    public String showUserList(Model model, Authentication authentication) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.findByUsername(authentication.getName()).get());

        return "users-info";
    }

    @PatchMapping("/admin/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update_user";
    }

    @RequestMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") int id, User user, Model model) {
        userService.save(user);
        return "redirect:/admin/users-info";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.delete(user.getId());
        return "redirect:/admin/users-info";
    }
}
