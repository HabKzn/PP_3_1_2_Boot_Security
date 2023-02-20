package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showSignUpForm() {
        return "redirect:/login";
    }

    @RequestMapping("/admin/adduser")
    public String addUser(User user) {
        userService.save(user);
        return "redirect:/admin/users-info";
    }

    @GetMapping("/admin/users-info")
    public String showUserList(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("users", userService.getAllUsers());
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user1 = userService.findByUsername(customUserDetails.getPassword()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user1);
        }
        model.addAttribute("newuser", new User());

        return "admin";
    }

    @PatchMapping("/admin/update/{id}")
    public String showUpdateForm(@PathVariable Optional<Integer> ids, Model model) {
        int id = ids.orElse(1);
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

    @RequestMapping("/admin/getone")
    @ResponseBody
    public User getOne(Integer id) {
        return userService.findById(id).get();
    }
}
