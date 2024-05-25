package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

import edu.url.salle.magdalena.morag.pokeapp.util.PokemonSharedPreferences;

public class Trainer {
    private int id;
    private String name;
    private int money;
    private ArrayList<Pokemon> pokedex;
    private ArrayList<String> items;
    private Context context;

    public Trainer(Context context, String name, int money,
                   ArrayList<String> items, ArrayList<Pokemon> pokedex) {
        this.context = context;
        this.name = name;
        this.money = money;
        this.items = items;
        this.pokedex = pokedex;
    }
    public Trainer(int id, String name, int money,
                   ArrayList<String> items, ArrayList<Pokemon> pokedex) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.items = items;
        this.pokedex = pokedex;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void addItem(String item) {
        items.add(item);
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

    public ArrayList<Pokemon> getPokedex() {
        return pokedex;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPokedex(ArrayList<Pokemon> pokedex) {
        this.pokedex = pokedex;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void releasePokemon(Pokemon pokemon) {
        if (pokedex.contains(pokemon)) {
            pokemon.setCaught(false);
            pokedex.remove(pokemon);
            PokemonSharedPreferences.saveCaughtPokemon(context, id, pokedex);
        } else {
            System.out.println("Pokemon not found in Pokedex.");
        }
    }

    public void interactWithPokemon(Pokemon pokemon, String pokeballType) {
        if (pokemon.isCaught()) {
            System.out.println("This Pokemon is already caught!");
            return;
        }

        if (pokedex.size() >= 6) {
            System.out.println("You already have 6 Pokémon. Release a Pokémon before capturing another.");
            return;
        }

        int captureProbability;
        switch (pokeballType) {
            case "Pokeball":
                captureProbability = (600 - pokemon.getType()) * 1 / 600;
                break;
            case "Superball":
                captureProbability = (600 - pokemon.getType()) * 3 / 600;
                break;
            case "Ultraball":
                captureProbability = (600 - pokemon.getType()) * 2 / 600;
                break;
            case "Masterball":
                captureProbability = 100;
                break;
            default:
                System.out.println("Invalid pokeball type.");
                return;
        }

        Random random = new Random();
        if (random.nextInt(100) < captureProbability) {
            pokemon.setCaught(true);
            pokedex.add(pokemon);
            PokemonSharedPreferences.saveCaughtPokemon(context, id, pokedex);
            System.out.println("You've captured " + pokemon.getName() + "!");
            calculateMoney(pokemon.getType());
        } else {
            System.out.println("The Pokémon broke free!");
        }
    }

    private void calculateMoney(int typePokemon) {
        money += 400 + 100 * typePokemon;
    }


}
