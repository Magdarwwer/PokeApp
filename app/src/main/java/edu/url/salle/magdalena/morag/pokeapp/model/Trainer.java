package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private String name;
    private int money;
    private List<Pokemon> capturedPokemons;
    private List<String> capturedPokemonsTrainer;
    private List<String> items;

    /*public Trainer(String name, int money, List<String> items, List<Pokemon> capturedPokemons) {
        this.name = name;
        this.money = money;
        this.items = items;
        this.capturedPokemons = capturedPokemons;
    }*/
    public Trainer(String name, int money, List<String> items, List<String> capturedPokemonsTrainer) {
        this.name = name;
        this.money = money;
        this.items = items;
        this.capturedPokemonsTrainer = capturedPokemonsTrainer;
    }


    public void updateName(String newName) {
        this.name = newName;

    }

    public void addMoney(int amount) {
        this.money += amount;

    }

    public void addItem(String item) {

    }

    public void capturePokemonFromAPI(Pokemon pokemon) {
            if (capturedPokemons.size() < 6) {
                capturedPokemons.add(pokemon);
            } else {

        }
    }
    public String capturePokemon(String pokemonName) {
        if (capturedPokemons.size() < 6) {
            capturedPokemons.add(new Pokemon(pokemonName));
        } else {
            System.out.println("Trainer can't capture more than 6 pokemons.");
        }
        return pokemonName;
    }


    public void releasePokemon(Pokemon pokemon) {
        if (capturedPokemons.size() > 1) {
            capturedPokemons.remove(pokemon);

        } else {

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Pokemon> getCapturedPokemons() {
        return capturedPokemons;
    }



    public void setCapturedPokemons(List<Pokemon> capturedPokemons) {
        this.capturedPokemons = capturedPokemons;
    }

    public List<String> getItems() {
        return items;
    }


    public void setItems(List<String> items) {
        this.items = items;
    }
}
