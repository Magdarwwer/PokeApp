package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.List;

public class Trainer {
    private String name;
    private int money;
    private List<Pokemon> capturedPokemons;
    private List<String> items;

    public Trainer(String name, int money, List<String> items, List<Pokemon> capturedPokemons) {
        this.name = name;
        this.money = money;
        this.items = items;
        this.capturedPokemons = capturedPokemons;
    }
    public void addPokemon(Pokemon pokemon) {
        capturedPokemons.add(pokemon);
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
    public String capturePokemon(Pokemon pokemon) {
        if (capturedPokemons.size() < 6) {
            capturedPokemons.add(pokemon);
        } else {
            System.out.println("Trainer can't capture more than 6 pokemons.");
        }
        return pokemon.getName();
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
