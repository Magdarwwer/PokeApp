package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon implements Parcelable {
    private int id;
    private String name;
    private String pokemonUrl;
    private String front_default;
    private String back_default;
    private int height;
    private int weight;
    private String description;
    private ArrayList<Type> typesList;
    private ArrayList<Ability> abilitiesList;
    private ArrayList<Stat> statsList;
    private boolean isCaught;
    private String pokeballType;

    public Pokemon(int id, String name, String pokemonUrl, String front_default, String back_default, String description, int height, int weight,
                   ArrayList<Type> typesList, ArrayList<Ability> abilitiesList, ArrayList<Stat> statsList) {
        this.id = id;
        this.name = name;
        this.pokemonUrl = pokemonUrl;
        this.front_default = front_default;
        this.back_default = back_default;
        this.description = description;
        this.height = height;
        this.weight = weight;
        this.typesList = typesList;
        this.abilitiesList = abilitiesList;
        this.statsList = statsList;
    }

    public Pokemon(int id, String name, boolean isCaught, String pokeballType) {
        this.id = id;
        this.name = name;
        this.isCaught = isCaught;
        this.pokeballType = pokeballType;
    }

    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pokemonUrl = in.readString();
        front_default = in.readString();
        back_default = in.readString();
        description = in.readString();
        height = in.readInt();
        weight = in.readInt();
        typesList = in.createTypedArrayList(Type.CREATOR);
        abilitiesList = in.createTypedArrayList(Ability.CREATOR);
        statsList = in.createTypedArrayList(Stat.CREATOR);
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public Pokemon(int pokemonId, String pokemonName) {
        this.id = pokemonId;
        this.name = pokemonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<Type> getTypesList() {
        return typesList;
    }

    public ArrayList<Ability> getAbilitiesList() {
        return abilitiesList;
    }

    public ArrayList<Stat> getStatsList() {
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

    public void setTypesList(ArrayList<Type> typesList) {
        this.typesList = typesList;
    }

    public void setAbilitiesList(ArrayList<Ability> abilitiesList) {
        this.abilitiesList = abilitiesList;
    }

    public void setStatsList(ArrayList<Stat> statsList) {
        this.statsList = statsList;
    }

    public boolean isCaught() {
        return isCaught;
    }

    public void setCaught(boolean caught) {
        isCaught = caught;
    }

    public String getPokeballType() {
        return pokeballType;
    }

    public void setPokeballType(String pokeballType) {
        this.pokeballType = pokeballType;
    }

    public int getType() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                return random.nextInt(61) + 20;
            case 1:
                return random.nextInt(121) + 80;
            case 2:
                return random.nextInt(151) + 200;
            case 3:
                return random.nextInt(151) + 350;
            default:
                return 0;
        }
    }

    public double getCaptureProbability(String pokeballType) {
        double baseProbability = (600.0 - getType()) / 600.0;
        switch (pokeballType) {
            case "Pokeball":
                return baseProbability * 1.0;
            case "Superball":
                return baseProbability * 1.5;
            case "Ultraball":
                return baseProbability * 2.0;
            case "Masterball":
                return 1.0; // 100% capture probability
            default:
                return 0.0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(pokemonUrl);
        dest.writeString(front_default);
        dest.writeString(back_default);
        dest.writeString(description);
        dest.writeInt(height);
        dest.writeInt(weight);
        dest.writeTypedList(typesList);
        dest.writeTypedList(abilitiesList);
        dest.writeTypedList(statsList);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Pokemon fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pokemon.class);
    }


}
