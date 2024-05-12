package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.List;
import java.util.Random;

public class Pokemon {
    private int id;
    private String name;
    private String imageUrl;
    private List<String> types;
    private String description;
    private List<String> abilities;
    private List<String> stats;
    private boolean isCaptured;
    private int frontImage;
    private int backImage;
    private String pokeball;
    private boolean isShiny;

    public Pokemon(int id, String name,  String imageUrl,
                   List<String> types, String description,
                   List<String> abilities, List<String> stats,
                   boolean isCaptured, int frontImage,
                   int backImage, String pokeball
                   ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.types = types;
        this.description = description;
        this.abilities = abilities;
        this.stats = stats;
        this.isCaptured = false;
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.pokeball = pokeball;
    }

    public Pokemon(String name, int frontImage, String imageUrl, boolean isShiny) {
        this.name = name;
        this.frontImage = frontImage;
        this.imageUrl = imageUrl;
        this.isShiny = isShiny;
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

    public int getFrontImage() {
        return frontImage;
    }

    public String getPokeball() {
        return pokeball;
    }

    public boolean isShiny() {
        return isShiny;
    }

    public int getBackImage() {
        return backImage;
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


}
