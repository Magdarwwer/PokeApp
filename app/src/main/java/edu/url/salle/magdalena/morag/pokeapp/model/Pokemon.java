package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pokemon implements Parcelable {
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

    public Pokemon(int id, String name, String pokemonUrl, String front_default, String back_default, int height, int weight,
                   ArrayList<String> typesList, ArrayList<String> abilitiesList, ArrayList<String> statsList) {
        this.id = id;
        this.name = name;
        this.pokemonUrl = pokemonUrl;
        this.front_default = front_default;
        this.back_default = back_default;
        this.height = height;
        this.weight = weight;
        this.typesList = typesList;
        this.abilitiesList = abilitiesList;
        this.statsList = statsList;
    }

    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pokemonUrl = in.readString();
        front_default = in.readString();
        back_default = in.readString();
        height = in.readInt();
        weight = in.readInt();
        typesList = in.createStringArrayList();
        abilitiesList = in.createStringArrayList();
        statsList = in.createStringArrayList();
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
        dest.writeInt(height);
        dest.writeInt(weight);
        dest.writeStringList(typesList);
        dest.writeStringList(abilitiesList);
        dest.writeStringList(statsList);
    }
}
