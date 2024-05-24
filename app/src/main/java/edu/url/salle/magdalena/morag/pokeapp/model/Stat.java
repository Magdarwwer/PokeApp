package edu.url.salle.magdalena.morag.pokeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {
    @SerializedName("stat")
    @Expose
    private Stat_ stat;
    @SerializedName("base_stat")
    @Expose
    private Integer baseStat;

    public Stat(Stat_ stat, Integer baseStat) {
        this.stat = stat;
        this.baseStat = baseStat;
    }

    public Stat_ getStat() {
        return stat;
    }

    public void setStat(Stat_ stat) {
        this.stat = stat;
    }

    public Integer getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(Integer baseStat) {
        this.baseStat = baseStat;
    }

}
