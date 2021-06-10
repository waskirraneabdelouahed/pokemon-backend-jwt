package com.nimbleways.pokemon.repository;

import com.nimbleways.pokemon.model.UserEntity;
import com.nimbleways.pokemon.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String username);
    public Optional<UserEntity> findByEmail(String email);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
