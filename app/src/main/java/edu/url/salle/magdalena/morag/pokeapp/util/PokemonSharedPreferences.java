package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonSharedPreferences {
    private static final String PREF_NAME_PREFIX = "Trainer_";
    private static final String KEY_POKEMON_LIST = "Pokemon_list";

    public static void saveCaughtPokemon(Context context, int trainerId, ArrayList<Pokemon> caughtPokemonList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME_PREFIX + trainerId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(caughtPokemonList);
        editor.putString(KEY_POKEMON_LIST, json);
        editor.apply();
    }

    public static ArrayList<Pokemon> loadCaughtPokemon(Context context, int trainerId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME_PREFIX + trainerId, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_POKEMON_LIST, null);
        Type type = new TypeToken<ArrayList<Pokemon>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
