package com.sbs.ald.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbs.ald.dto.LoginDto;
import com.sbs.ald.dto.Role;
import com.sbs.ald.dto.User;
import com.sbs.ald.repository.RoleRepository;
import com.sbs.ald.repository.UserRepository;
import com.sbs.ald.util.JwtUtil;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public Optional<User> createUser2(LoginDto loginDto) {
        // Proverite da li korisnik sa datim korisničkim imenom već postoji
        if (userRepository.findByUsername(loginDto.getUsername()).isPresent()) {
            return Optional.empty(); // Vratite prazan Optional ako korisnik već postoji
        }

        // Kreiranje skupa uloga
        Set<Role> roles = new HashSet<>();
        for (Role role : loginDto.getRoles()) {
            Optional<Role> roleOptional = roleService.findByName(role.getName());
            if (roleOptional.isPresent()) {
                roles.add(roleOptional.get());
            } else {
                return Optional.empty(); // Vratite prazan Optional ako neka od uloga nije pronađena
            }
        }

        // Kreirajte novog korisnika sa svim informacijama
        User newUser = new User(
            loginDto.getUsername(),
            passwordEncoder.encode(loginDto.getPassword()),
            loginDto.getName(),
            loginDto.getAddress(),
            loginDto.getEmail(),
            roles // Dodajte uloge korisniku
        );

        // Sačuvajte korisnika u bazi podataka
        User savedUser = userRepository.save(newUser);

        // Vratite sačuvanog korisnika unutar Optional
        return Optional.of(savedUser);
    }

    // Provera da li korisnik već postoji
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Metoda za čuvanje korisnika
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<String> signin(String username, String password) {
        // Inicijalizacija tokena
        Optional<String> token = Optional.empty();
     
        // Provera da li korisnik postoji
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if (user.get().getRoles() == null || user.get().getRoles().isEmpty()) {
            throw new IllegalStateException("User has no roles assigned");
        }

        try {
            // Autentifikacija korisnika
        	System.out.println("Username: " + username);
        	System.out.println("Password: " + password);

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    username,
                    password
                )
            );

            // Generisanje JWT tokena
//            token = Optional.of(jwtUtil.generateToken(user.get()));
            token = Optional.of(jwtUtil.createToken(username, new ArrayList<>(user.get().getRoles())));

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }

        return token;
    }
    

}
