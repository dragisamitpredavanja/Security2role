package com.sbs.ald.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbs.ald.dto.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
//    Role findByName(String name);  // Pronalaženje rola po imenu
    boolean existsByName(String name);  // Provera da li rola postoji
}

