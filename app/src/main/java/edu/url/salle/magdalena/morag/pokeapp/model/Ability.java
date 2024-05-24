package edu.url.salle.magdalena.morag.pokeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ability {
    @SerializedName("ability")
    @Expose
    private Ability_ ability;

    public Ability(Ability_ ability) {
        this.ability = ability;
    }

    public Ability_ getAbility() {
        return ability;
    }

    public void setAbility(Ability_ ability) {
        this.ability = ability;
    }
}
