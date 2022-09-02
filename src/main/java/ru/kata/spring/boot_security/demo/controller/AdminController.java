package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder encoder){
        this.userService = userService;
        this.roleService = roleService;
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
                           @ModelAttribute("allRoles") String[] role) {
        System.out.println(Arrays.toString(role));
        if (role != null) {
            Set<Role> rolesSet = new HashSet<>();
            for (String roles : role) {
                rolesSet.add(roleService.findRoleByName(roles));
            }
            user.setRoles(rolesSet);

        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delite")
    private String delitUser(@ModelAttribute("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
