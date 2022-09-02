package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleService {

    public Set<Role> getAll();
   Role findRoleByName(String role);

}
