package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;

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
    private String evolutionStage;
    private int type;
    private Pokeball pokeball;

    public Pokemon(int id, String name, String evolutionStage) {
        this.id = id;
        this.name = name;
        this.evolutionStage = evolutionStage;
        this.type = Pokeball.generatePokemonType(evolutionStage);
    }

    public Pokemon(int pokemonId, String pokemonName) {
        this.id = pokemonId;
        this.name = pokemonName;
    }

    public Pokemon(int id, String name, boolean isCaught, Pokeball pokeball) {
        this.id = id;
        this.name = name;
        this.isCaught = isCaught;
        this.pokeball = pokeball;
        this.type = Pokeball.generatePokemonType("First Evolution");
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
        isCaught = in.readByte() != 0;
        evolutionStage = in.readString();
        type = in.readInt();
        pokeball = new Pokeball(in.readString());
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


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }


    public boolean isCaught() {
        return isCaught;
    }

    public String getEvolutionStage() {
        return evolutionStage;
    }

    public Pokeball getPokeball() {
        return pokeball;
    }

    public void setCaught(boolean caught) {
        isCaught = caught;
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
        dest.writeByte((byte) (isCaught ? 1 : 0));
        dest.writeString(evolutionStage);
        dest.writeInt(type);
        if (pokeball != null) {
            dest.writeString(pokeball.getType());
        } else {
            dest.writeString(null);
        }
    }


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Pokemon fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pokemon.class);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPokemonUrl() {
        return pokemonUrl;
    }

    public void setPokemonUrl(String pokemonUrl) {
        this.pokemonUrl = pokemonUrl;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Type> getTypesList() {
        return typesList;
    }

    public void setTypesList(ArrayList<Type> typesList) {
        this.typesList = typesList;
    }

    public ArrayList<Ability> getAbilitiesList() {
        return abilitiesList;
    }

    public void setAbilitiesList(ArrayList<Ability> abilitiesList) {
        this.abilitiesList = abilitiesList;
    }

    public ArrayList<Stat> getStatsList() {
        return statsList;
    }

    public void setStatsList(ArrayList<Stat> statsList) {
        this.statsList = statsList;
    }

    public void setEvolutionStage(String evolutionStage) {
        this.evolutionStage = evolutionStage;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPokeballType() {
        if (pokeball != null) {
            return pokeball.getType();
        }
        return null;
    }

    public void setPokeball(Pokeball pokeball) {
        this.pokeball = pokeball;
    }
}
