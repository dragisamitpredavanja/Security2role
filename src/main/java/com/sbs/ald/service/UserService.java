package com.sbs.ald.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sbs.ald.dto.LoginDto;
import com.sbs.ald.dto.User;
import com.sbs.ald.repository.RoleRepository;
import com.sbs.ald.repository.UserRepository;
import com.sbs.ald.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;


    // Provera da li korisnik već postoji
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Metoda za čuvanje korisnika
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<String> signin(LoginDto loginDto) {
        // Inicijalizacija tokena
        Optional<String> token = Optional.empty();

        // Provera da li korisnik postoji
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + loginDto.getUsername());
        }

        try {
            // Autentifikacija korisnika
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
                )
            );

            // Generisanje JWT tokena
            token = Optional.of(jwtUtil.generateToken(user.get()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }

        return token;
    }

}
