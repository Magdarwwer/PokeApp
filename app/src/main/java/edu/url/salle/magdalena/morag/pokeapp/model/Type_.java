package edu.url.salle.magdalena.morag.pokeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type_ {
    @SerializedName("name")
    @Expose
    private String name;

    public Type_(String typeName) {
        this.name = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
