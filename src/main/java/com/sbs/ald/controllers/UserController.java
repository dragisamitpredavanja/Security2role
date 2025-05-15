package com.sbs.ald.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional; // ✔️ ISPRAVNO
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.sbs.ald.dto.LoginDto;
import com.sbs.ald.dto.OsobaDTO;
import com.sbs.ald.dto.Role;
import com.sbs.ald.dto.User;
import com.sbs.ald.entitety.Osoba;
import com.sbs.ald.repository.OsobaRepository;
import com.sbs.ald.service.RoleService;
import com.sbs.ald.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
//    private final OsobaRepository osobaRepository;
//    public UserController(OsobaRepository osobaRepository) {
//        this.osobaRepository = osobaRepository;
//    }
    @Autowired
    private RoleService roleService;
//    @Autowired
//    private RoleRepository roleRepository;

    // Endpoint za kreiranje korisnika sa rolama 'admin' ili 'user'
    @PostMapping("/createUser1")
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
    @PostMapping("/createUser")
    @CrossOrigin
	public User createUser(@RequestBody LoginDto loginDto) {

		return userService.createUser2(loginDto)
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}

    @CrossOrigin
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

   


}
