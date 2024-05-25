package edu.url.salle.magdalena.morag.pokeapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private String pokemonUrl;
    private String front_default;
    private String back_default;
    private int height;
    private int weight;
    private ArrayList<String> typesList;
    private ArrayList<String> abilitiesList;
    private ArrayList<String> statsList;

    public Pokemon(int id, String name, String front_default, String back_default, int height, int weight,
                   ArrayList<String> typesList, ArrayList<String> abilitiesList, ArrayList<String> statsList) {
        this.id = id;
        this.name = name;
        this.front_default = front_default;
        this.back_default = back_default;
        this.height = height;
        this.weight = weight;
        this.typesList = typesList;
        this.abilitiesList = abilitiesList;
        this.statsList = statsList;
    }

    public Pokemon(int pokemonId, String pokemonName) {
        this.id = pokemonId;
        this.name = pokemonName;
    }

    public String getPokemonUrl() {
        return pokemonUrl;
    }

    public void setPokemonUrl(String pokemonUrl) {
        this.pokemonUrl = pokemonUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFront_default() {
        return front_default;
    }

    public String getBack_default() {
        return back_default;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public ArrayList<String> getTypesList() {
        return typesList;
    }

    public ArrayList<String> getAbilitiesList() {
        return abilitiesList;
    }

    public ArrayList<String> getStatsList() {
        return statsList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTypesList(ArrayList<String> typesList) {
        this.typesList = typesList;
    }

    public void setAbilitiesList(ArrayList<String> abilitiesList) {
        this.abilitiesList = abilitiesList;
    }

    public void setStatsList(ArrayList<String> statsList) {
        this.statsList = statsList;
    }


}
