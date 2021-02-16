package com.nimbleways.pokemon.controler;

import com.nimbleways.pokemon.entitie.Pokemon;
import com.nimbleways.pokemon.service.PokemonService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PokemonContoller {
    private PokemonService service;

    public PokemonContoller(PokemonService service) {
        this.service = service;
    }
    @GetMapping("/pokemon")
    public List<Pokemon> getAll(){
        return service.getAll();
    }
}
