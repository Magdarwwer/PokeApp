package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.ArrayList;
import java.util.Random;

public class Trainer {
    private String name;
    private int money;
    private ArrayList<String> items;
    private ArrayList<Pokemon> pokedex;

    public Trainer(String name, int money, ArrayList<String> items, ArrayList<Pokemon> pokedex) {
        this.name = name;
        this.money = money;
        this.items = items;
        this.pokedex = pokedex;
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

    public ArrayList<String> getItems() {
        return items;
    }


    public ArrayList<Pokemon> getPokedex() {
        return pokedex;
    }

    public void capturePokemon(Pokemon pokemon, Pokeball pokeball) {
        if (pokedex.size() >= 6) {
            return;
        }

        double captureProbability = pokeball.getCaptureProbability(pokemon.getType());
        if (new Random().nextDouble() <= captureProbability || pokeball.getType().equals("Masterball")) {
            pokemon.setCaught(true);
            pokedex.add(pokemon);
            money += 400 + 100 * pokemon.getType();
        }
    }

    public void releasePokemon(int index) {
        if (index >= 0 && index < pokedex.size()) {
            pokedex.remove(index);
        }
    }

    public void addItem(String itemName) {
        items.add(itemName);
    }
}
