package com.sbs.ald.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.ald.dto.User;



public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    boolean existsByUsername(String username); // Provera da li korisnik već postoji
    List<User> findAll();
    
}