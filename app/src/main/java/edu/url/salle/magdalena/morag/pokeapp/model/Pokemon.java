package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.Random;

public class Pokemon {

    private String name;
    private int frontImage;
    private boolean isShiny;

    public Pokemon(String name, int frontImage) {
        this.name = name;
        this.frontImage = frontImage;
        this.isShiny = generateShinyStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(int frontImage) {
        this.frontImage = frontImage;
    }

    public boolean isShiny() {
        return isShiny;
    }

    private boolean generateShinyStatus() {
        Random random = new Random();
        int randomNumber = random.nextInt(500) + 1;
        return randomNumber == 1;
    }
}