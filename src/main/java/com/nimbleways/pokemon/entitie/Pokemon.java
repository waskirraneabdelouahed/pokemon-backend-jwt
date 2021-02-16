package com.nimbleways.pokemon.entitie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pokemon {
    @Id
    private long id;
    private String name;
    private int height;
    private int weight;
}
