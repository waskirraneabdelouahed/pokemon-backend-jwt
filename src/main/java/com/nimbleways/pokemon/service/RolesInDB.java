package com.nimbleways.pokemon.service;

import com.nimbleways.pokemon.ERoles.ERole;
import com.nimbleways.pokemon.model.Role;
import com.nimbleways.pokemon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesInDB {
    @Autowired
    private RoleRepository roleRepository;
    public void initRole(){
        roleRepository.save(new Role(ERole.ROLE_USER));
        roleRepository.save(new Role(ERole.ROLE_ADMIN));

    }
}
