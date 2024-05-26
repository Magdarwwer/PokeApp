package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonSharedPreferences {

    private static final String PREF_NAME = "PokemonPrefs";
    private static final String CAUGHT_POKEMON_KEY_PREFIX = "caught_pokemon_";

    public static void saveCaughtPokemon(Context context, int trainerId, ArrayList<Pokemon> pokedex) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pokedex);
        editor.putString(CAUGHT_POKEMON_KEY_PREFIX + trainerId, json);
        editor.apply();
    }

    public static ArrayList<Pokemon> loadCaughtPokemon(Context context, int trainerId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CAUGHT_POKEMON_KEY_PREFIX + trainerId, null);
        Type type = new TypeToken<ArrayList<Pokemon>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
