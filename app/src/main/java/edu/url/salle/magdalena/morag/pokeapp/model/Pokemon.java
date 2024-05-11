package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.Random;

public class Pokemon {

    private String name;
    private int frontImage;
    private int backImage;
    private boolean isShiny;

    public Pokemon(String name, int frontImage, int backImage) {
        this.name = name;
        this.frontImage = frontImage;
        this.backImage = backImage;
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
    public int getBackImage() {
        return backImage;
    }

    public void setFrontImage(int frontImage) {
        this.frontImage = frontImage;
    }

    public boolean isShiny() {
        return isShiny;
    }

}