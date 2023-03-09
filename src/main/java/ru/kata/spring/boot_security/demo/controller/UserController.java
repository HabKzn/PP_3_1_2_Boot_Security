package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/userView", method = RequestMethod.GET)
    public String getIndex(Model model, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        User user1 = userService.findByUsername(customUserDetails.getPassword()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user1);
        return "user";
    }

}
