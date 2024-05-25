package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Ability implements Parcelable {
    private String name;

    public static final Parcelable.Creator<Ability> CREATOR = new Parcelable.Creator<Ability>() {
        @Override
        public Ability createFromParcel(Parcel in) {
            return new Ability(in.readString());
        }

        @Override
        public Ability[] newArray(int size) {
            return new Ability[size];
        }
    };

    public Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
    }
}
