package com.nimbleways.pokemon.service;

import com.nimbleways.pokemon.entitie.Pokemon;
import com.nimbleways.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Service
@CrossOrigin("*")
public class PokemonService {
    private PokemonRepository pokemonRepository;
    private String data="C:\\Users\\TAOUFIQ\\IdeaProjects\\NimbleWays\\pokemon\\src\\main\\resources\\data.txt";
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public void initPokemon(){
        try {
            File myObj = new File(data);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("[,]");
                Pokemon p=new Pokemon(Long.parseLong(data[0]),data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                this.pokemonRepository.save(p);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public List<Pokemon> getAll(){
        return this.pokemonRepository.findAll();
    }
}
