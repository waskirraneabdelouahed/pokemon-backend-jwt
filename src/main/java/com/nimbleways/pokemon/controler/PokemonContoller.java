package com.nimbleways.pokemon.controler;

import com.nimbleways.pokemon.entitie.Pokemon;
import com.nimbleways.pokemon.model.PokemonForNotAdmins;
import com.nimbleways.pokemon.service.PokemonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("pokemon")
public class PokemonContoller {
    private PokemonService service;

    public PokemonContoller(PokemonService service) {
        this.service = service;
    }
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Pokemon> getAllAdmin(){
        return service.getAll();
    }

    @GetMapping("view")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<PokemonForNotAdmins> getAll(){
        List<PokemonForNotAdmins> list = service.getAll().stream().map(pokemon -> new PokemonForNotAdmins(pokemon.getId(),pokemon.getName())).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Pokemon getPoke(@PathVariable("id") long id){
        return service.getPokeById(id);
    }

    @GetMapping("view/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PokemonForNotAdmins getAll(@PathVariable("id") long id){
        Pokemon pokemon = service.getPokeById(id);
        PokemonForNotAdmins poke = new PokemonForNotAdmins(pokemon.getId(), pokemon.getName());
        return poke;
    }

}

