package edu.url.salle.magdalena.morag.pokeapp.model;

import android.content.SharedPreferences;

import java.util.ArrayList;


import java.util.HashSet;
import java.util.Set;


public class TrainerManager {
    private ArrayList<Trainer> trainers;
    private Trainer activeTrainer;

    public TrainerManager() {
        trainers = new ArrayList<>();
        Trainer trainer1 = new Trainer("Ash", 1000, new ArrayList<>(), new ArrayList<>());
        trainers.add(trainer1);

        activeTrainer = trainer1;
    }

    public Trainer getActiveTrainer() {
        return activeTrainer;
    }

    public void setActiveTrainer(Trainer trainer) {
        activeTrainer = trainer;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    public Trainer loadTrainerData(String name, int money, Set<String> itemSet, Set<String> pokemonSet) {
        ArrayList<String> items = new ArrayList<>(itemSet);
        ArrayList<Pokemon> pokedex = new ArrayList<>();
        for (String pokemonJson : pokemonSet) {
            Pokemon pokemon = Pokemon.fromJson(pokemonJson);
            pokedex.add(pokemon);
        }
        return new Trainer(name, money, items, pokedex);
    }

    public void saveTrainerData(Trainer trainer, SharedPreferences sharedPreferences) {
        if (trainer != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
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
