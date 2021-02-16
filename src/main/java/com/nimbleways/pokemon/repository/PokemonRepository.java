package com.nimbleways.pokemon.repository;

import com.nimbleways.pokemon.entitie.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {
}
