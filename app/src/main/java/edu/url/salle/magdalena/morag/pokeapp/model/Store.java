package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {
    public static int POKEBALL_PRICE = 200;
    public static int SUPERBALL_PRICE = 500;
    public static int ULTRABALL_PRICE = 1500;
    public static int MASTERBALL_PRICE = 100000;


    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public Store() {
    }

    protected Store(Parcel in) {
        POKEBALL_PRICE = in.readInt();
        SUPERBALL_PRICE = in.readInt();
        ULTRABALL_PRICE = in.readInt();
        MASTERBALL_PRICE = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(POKEBALL_PRICE);
        dest.writeInt(SUPERBALL_PRICE);
        dest.writeInt(ULTRABALL_PRICE);
        dest.writeInt(MASTERBALL_PRICE);
    }
}
