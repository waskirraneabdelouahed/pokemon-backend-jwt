package com.nimbleways.pokemon.service;

import com.nimbleways.pokemon.model.UserEntity;
import com.nimbleways.pokemon.model.UserEntity;
import com.nimbleways.pokemon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class UsersDB {
    @Autowired
    private UserRepository userRepository;
    public void initRole(){
        userRepository.save(new UserEntity("email","username","password",new HashSet<>()));
    }
}
