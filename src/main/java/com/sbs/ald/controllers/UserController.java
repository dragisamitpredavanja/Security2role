package com.sbs.ald.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbs.ald.dto.Role;
import com.sbs.ald.dto.User;
import com.sbs.ald.repository.RoleRepository;
import com.sbs.ald.service.RoleService;
import com.sbs.ald.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
//    @Autowired
//    private RoleRepository roleRepository;

    // Endpoint za kreiranje korisnika sa rolama 'admin' ili 'user'
    @PostMapping("/createUser")
    public ResponseEntity<String> createUserWithRoles(@RequestBody User user) {
        // Provera da li korisnik već postoji
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Provera da li su role validne
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Optional<Role> roleOptional = roleService.findByName(role.getName());
            if (roleOptional.isPresent()) {
                roles.add(roleOptional.get());
            } else {
                return ResponseEntity.badRequest().body("Invalid role= " + role.getName());
            }
        }

        // Postavljanje uloga korisniku
        user.setRoles(roles);

        // Kreiranje korisnika
        userService.save(user);

        return ResponseEntity.ok("User created successfully with roles: " + roles);
    }

}
