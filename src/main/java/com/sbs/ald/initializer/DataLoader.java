package com.sbs.ald.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sbs.ald.dto.Role;
import com.sbs.ald.service.RoleService;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleService roleService;

    public DataLoader(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
    	if (!roleService.findByName("ROLE_SUPER_ADMIN").isPresent()) {
            roleService.save(new Role("ROLE_SUPER_ADMIN"));
        }
        if (!roleService.findByName("ROLE_ADMIN").isPresent()) {
            roleService.save(new Role("ROLE_ADMIN"));
        }
        if (!roleService.findByName("ROLE_USER").isPresent()) {
            roleService.save(new Role("ROLE_USER"));
        }
    }
}

