package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.List;
import java.util.Random;

public class PokemonDetailsResponse {
    private String name;
    private int id;
    private String imageUrl;
    private List<String> types;
    private String description;
    private List<String> abilities;
    private List<String> stats;
    private boolean isCaptured;

    public PokemonDetailsResponse(String name, int id, String imageUrl,
                                  List<String> types, String description,
                                  List<String> abilities,
                                  List<String> stats, boolean isCaptured) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.types = types;
        this.description = description;
        this.abilities = abilities;
        this.stats = stats;
        this.isCaptured = isCaptured;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public List<String> getStats() {
        return stats;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public String chooseAbility() {
        Random random = new Random();
        int randomValue = random.nextInt(100);

        if (abilities.size() == 1) {
            return abilities.get(0);
        } else {
            if (randomValue < 25) {
                return abilities.get(abilities.size() - 1); // Hidden ability
            } else if (randomValue < 75) {
                return abilities.get(random.nextInt(abilities.size() - 1));
            } else {
                return abilities.get(random.nextInt(abilities.size() - 1));
            }
        }
    }
}
