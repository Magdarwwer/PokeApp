package edu.url.salle.magdalena.morag.pokeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {
    // Prices of items in the store
    public static final int POKEBALL_PRICE = 200;
    public static final int SUPERBALL_PRICE = 500;
    public static final int ULTRABALL_PRICE = 1500;
    public static final int MASTERBALL_PRICE = 100000;

    // Constructor
    public Store() {
        // Default constructor
    }

    // Parcelable methods
    protected Store(Parcel in) {
        // No additional fields to read
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // No additional fields to write
    }
}
