package com.nimbleways.pokemon;

import com.nimbleways.pokemon.service.PokemonService;
import com.nimbleways.pokemon.service.RolesInDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PokemonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PokemonService service, RolesInDB serviceDB){
        return args -> {
          service.initPokemon();
          serviceDB.initRole();
        };
    }
}
