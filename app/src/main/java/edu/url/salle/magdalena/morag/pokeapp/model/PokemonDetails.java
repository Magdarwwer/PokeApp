package edu.url.salle.magdalena.morag.pokeapp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class PokemonDetails implements Serializable {
    private int id;
    private String name;
    private int height;
    private int weight;
    private String url;
    private List<String> types;
    private String description;
    private List<String> abilities;
    private List<String> stats;
    private boolean isCaptured;
    private String front_default;
    private String back_default;
    private String selectedAbility;

    public PokemonDetails(int id, String name, String imageUrl,
                          List<String> types, String description,
                          List<String> abilities, List<String> stats) {
        this.id = id;
        this.name = name;
        this.url = imageUrl;
        this.types = types;
        this.description = description;
        this.abilities = abilities;
        this.stats = stats;
        this.isCaptured = false;
    }

    public PokemonDetails(String name, int height, int weight, String front_default,
                          String back_default, List<String> types, String description,
                          List<String> abilities, List<String> stats) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.front_default = front_default;
        this.back_default = back_default;
        this.types = types;
        this.description = description;
        this.abilities = abilities;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
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

    public String getFront_default() {
        return front_default;
    }

    public String getBack_default() {
        return back_default;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void capturePokemon() {
        isCaptured = true;
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

    public void setSelectedAbility(String selectedAbility) {
        this.selectedAbility = selectedAbility;
    }

    public String getSelectedAbility() {
        return selectedAbility;
    }
}
