package edu.url.salle.magdalena.morag.pokeapp.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TrainerManager {
    private static TrainerManager instance;
    private ArrayList<Trainer> trainers;
    private Trainer activeTrainer;

    private TrainerManager() {
        trainers = new ArrayList<>();

        // Create and add trainer
        Trainer trainer1 = new Trainer("Ash", 1000, new ArrayList<>(), new ArrayList<>());

        trainer1.getItems().add("Potion");
        trainer1.getItems().add("Revive");
        trainer1.getItems().add("Pokeball");

        trainer1.getPokedex().add(new Pokemon(25, "Pikachu", "First Evolution"));
        trainer1.getPokedex().add(new Pokemon(4, "Charmander", "First Evolution"));
        trainer1.getPokedex().add(new Pokemon(1, "Bulbasaur", "First Evolution"));

        trainer1.capturePokemon(trainer1.getPokedex().get(0), new Pokeball("Pokeball"));
        trainer1.capturePokemon(trainer1.getPokedex().get(1), new Pokeball("Ultraball"));
        trainer1.capturePokemon(trainer1.getPokedex().get(2), new Pokeball("Ultraball"));

        trainers.add(trainer1);

        activeTrainer = trainer1;
    }

    public static synchronized TrainerManager getInstance() {
        if (instance == null) {
            instance = new TrainerManager();
        }
        return instance;
    }

    public Trainer getActiveTrainer() {
        return activeTrainer;
    }

    public void setActiveTrainer(Trainer activeTrainer) {
        this.activeTrainer = activeTrainer;
    }

    public Trainer loadTrainerData(String name, int money, Set<String> itemSet, Set<String> pokemonSet) {
        if (name.isEmpty()) {
            return null;
        }

        ArrayList<String> items = new ArrayList<>(itemSet);
        ArrayList<Pokemon> pokedex = new ArrayList<>();
        Gson gson = new Gson();
        for (String pokemonJson : pokemonSet) {
            Pokemon pokemon = gson.fromJson(pokemonJson, Pokemon.class);
            pokedex.add(pokemon);
        }

        activeTrainer = new Trainer(name, money, items, pokedex);
        return activeTrainer;
    }

    public void saveTrainerData(Trainer trainer, SharedPreferences.Editor editor) {
        if (trainer != null) {
            editor.putString("trainerName", trainer.getName());
            editor.putInt("trainerMoney", trainer.getMoney());
            editor.putStringSet("trainerItems", new HashSet<>(trainer.getItems()));

            Set<String> pokemonSet = new HashSet<>();
            for (Pokemon pokemon : trainer.getPokedex()) {
                pokemonSet.add(pokemon.toJson());
            }
            editor.putStringSet("trainerPokedex", pokemonSet);

            editor.apply();
        }
    }
}