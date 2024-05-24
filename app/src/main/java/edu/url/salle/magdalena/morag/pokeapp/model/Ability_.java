package edu.url.salle.magdalena.morag.pokeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ability_ {
    @SerializedName("name")
    @Expose
    private String name;

    public Ability_(String abilityName) {
        this.name = abilityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
