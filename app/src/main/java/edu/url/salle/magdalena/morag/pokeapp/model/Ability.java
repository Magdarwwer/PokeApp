package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Ability implements Parcelable {
    private String name;
    private boolean isHidden;

    public static final Parcelable.Creator<Ability> CREATOR = new Parcelable.Creator<Ability>() {
        @Override
        public Ability createFromParcel(Parcel in) {
            return new Ability(in);
        }

        @Override
        public Ability[] newArray(int size) {
            return new Ability[size];
        }
    };

    public Ability(String name, boolean isHidden) {
        this.name = name;
        this.isHidden = isHidden;
    }

    public Ability(Parcel in) {
        name = in.readString();
        isHidden = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isHidden ? 1 : 0));
    }

    public static boolean isHiddenAbility() {
        Random random = new Random();
        int probability = random.nextInt(100);
        return probability < 25;
    }
}
