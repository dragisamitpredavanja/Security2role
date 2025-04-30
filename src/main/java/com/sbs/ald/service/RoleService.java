package com.sbs.ald.service;

import com.sbs.ald.dto.Role;
import com.sbs.ald.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Metoda za pronalaženje uloge po imenu
    public  Optional<Role>  findByName(String name) {
        return roleRepository.findByName(name);
    }

    // Metoda za kreiranje nove uloge
    public Role createRole(String roleName) {
        // Proverite da li rola već postoji
        if (roleRepository.existsByName(roleName)) {
            return null;  // Ako rola već postoji, ne kreiraj novu
        }

        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

	public void save(Role role) {
		roleRepository.save(role);
		// TODO Auto-generated method stub
		
	}
}
