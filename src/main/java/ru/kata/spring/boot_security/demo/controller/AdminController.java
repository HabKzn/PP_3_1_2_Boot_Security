package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
            model.addAttribute("listRoles", roleService.listRoles());
            model.addAttribute("users", userService.getAllUsers());
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user1 = userService.findByUsername(customUserDetails.getPassword()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user1);
        }
        model.addAttribute("newuser", new User());
        return "admin";
    }

    @PostMapping("/admin/update/{id}")
    public String update(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/users-info";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.delete(user.getId());
        return "redirect:/admin/users-info";
    }
}
