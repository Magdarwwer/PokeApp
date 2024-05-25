package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Stat implements Parcelable {
    private String name;
    private int baseStat;

    public static final Parcelable.Creator<Stat> CREATOR = new Parcelable.Creator<Stat>() {
        @Override
        public Stat createFromParcel(Parcel in) {
            return new Stat(in.readString(), in.readInt());
        }

        @Override
        public Stat[] newArray(int size) {
            return new Stat[size];
        }
    };
    public Stat(String name, int baseStat) {
        this.name = name;
        this.baseStat = baseStat;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(baseStat);
    }

}
