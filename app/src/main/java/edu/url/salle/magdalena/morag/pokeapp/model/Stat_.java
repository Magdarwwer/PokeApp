package edu.url.salle.magdalena.morag.pokeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat_ {
    @SerializedName("name")
    @Expose
    private String name;

    public Stat_(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
