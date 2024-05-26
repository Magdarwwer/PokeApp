package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.Random;

public class Pokeball {
    private String type;

    public Pokeball(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double getCaptureProbability(int pokemonType) {
        double baseProbability = (600.0 - pokemonType) / 600.0;
        switch (type) {
            case "Pokeball":
                return baseProbability * 1.0;
            case "Superball":
                return baseProbability * 1.5;
            case "Ultraball":
                return baseProbability * 2.0;
            case "Masterball":
                return 1.0;
            default:
                return 0.0;
        }
    }

    public static int generatePokemonType(String evolutionStage) {
        Random random = new Random();
        switch (evolutionStage) {
            case "First Evolution":
                return random.nextInt(61) + 20;
            case "Second Evolution":
                return random.nextInt(121) + 80;
            case "Third Evolution":
                return random.nextInt(151) + 200;
            case "Legendary":
                return random.nextInt(151) + 350;
            default:
                return 0;
        }
    }

}
