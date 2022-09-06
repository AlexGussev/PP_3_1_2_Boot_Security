package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder encoder){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = encoder;
    }

    @GetMapping("/admin")
    public String getAdminView(Model model) {
        model.addAttribute("allUser", userService.getAllUsers());
        return "admin/admin-view";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAll());
        return "/admin/user-info";
    }

    @PostMapping("/admin/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("allRoles") String[] role) {
        if (role != null) {
            Set<Role> rolesSet = new HashSet<>();
            for (String roles : role) {
                rolesSet.add(roleService.findRoleByName(roles));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(rolesSet);
        } else {
            user.setRoles(roleService.getRole(1));
        }
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/admin/update")
    public String addNewUser(@Param("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAll());
        return "/admin/user-info";
    }

    @GetMapping("/admin/delite")
    private String delitUser(@ModelAttribute("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
