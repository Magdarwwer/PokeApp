package edu.url.salle.magdalena.morag.pokeapp.model;

import java.util.ArrayList;

public class Trainer {

    private int id;
    private String name;
    private int money;
    private ArrayList<Pokemon> pokedex;
    private ArrayList<String> items;


    public Trainer() {
        // Default constructor required by Room
    }

    public Trainer(String name, int money,
                   ArrayList<String> items, ArrayList<Pokemon> pokedex) {
        this.name = name;
        this.money = money;
        this.items = items;
        this.pokedex = pokedex;
    }

    public Trainer(int id, String name, int money, ArrayList<String> items, ArrayList<Pokemon> pokedex) {
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
    public ArrayList<Pokemon> getCapturedPokemons() {
        return pokedex;

    }
}
