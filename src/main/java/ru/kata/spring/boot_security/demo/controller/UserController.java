package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;
    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/user")
    public String getUserView(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("users", user);
        return "/user/user-view";
    }

    @GetMapping("/user/update")
    public String userUpdate(@Param("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user/user-form";
    }

    @PostMapping("/user/save")
    public String userSave(@ModelAttribute("user") User user, Principal principal) {
        User users = userRepository.findByUsername(principal.getName());
        user.setRoles(users.getRoles());
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/user/delite")
    public String userDelit(@ModelAttribute("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }
}
