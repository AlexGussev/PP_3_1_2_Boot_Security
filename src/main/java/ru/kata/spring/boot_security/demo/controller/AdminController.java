package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAdminView(Model model) {
        model.addAttribute("allUser", userService.getAllUsers());
        return "admin/admin-view";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", new Role());
        return "/admin/user-info";
    }

    @PostMapping("/admin/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @ModelAttribute("allRoles") Role role) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
