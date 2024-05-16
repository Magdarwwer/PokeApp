package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.R;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class PokemonFragment extends Fragment implements PokemonAdapter.OnPokemonClickListener {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemonList;

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokedex, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(requireContext());
        adapter.setOnPokemonClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fetchData();

        EditText editTextSearch = root.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Intentionally empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                searchPokemon(searchText);
            }
        });

        return root;
    }

    private void searchPokemon(String searchText) {
        ArrayList<Pokemon> filteredPokemonList = new ArrayList<>();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredPokemonList.add(pokemon);
            }
        }
        adapter.filterList(filteredPokemonList);
    }

    public void fetchData() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = BASE_URL + "pokemon?limit=15&offset=0";
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject pokemonJson = results.getJSONObject(i);
                        int pokemonID = i + 1;
                        String pokemonName = pokemonJson.getString("name");
                        String pokemonUrl = pokemonJson.getString("url");
                        Pokemon pokemon = new Pokemon(pokemonName, pokemonUrl);
                        pokemon.setId(pokemonID);
                        pokemonList.add(pokemon);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(requireContext(), "Failed to fetch Pokémon data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchPokemonDetails(Pokemon pokemon, PokemonDetailsCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = BASE_URL + "pokemon/" + pokemon.getId();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String name = response.getString("name");
                    int height = response.getInt("height");
                    int weight = response.getInt("weight");

                    JSONObject sprites = response.getJSONObject("sprites");
                    String front_default = sprites.getString("front_default");
                    String back_default = sprites.getString("back_default");

                    JSONArray typesArray = response.getJSONArray("types");
                    ArrayList<String> typesList = new ArrayList<>();
                    for (int i = 0; i < typesArray.length(); i++) {
                        JSONObject typeObject = typesArray.getJSONObject(i);
                        String typeName = typeObject.getJSONObject("type").getString("name");
                        typesList.add(typeName);
                    }

                    JSONArray abilitiesArray = response.getJSONArray("abilities");
                    ArrayList<String> abilitiesList = new ArrayList<>();
                    for (int i = 0; i < abilitiesArray.length(); i++) {
                        JSONObject abilityObject = abilitiesArray.getJSONObject(i);
                        String abilityName = abilityObject.getJSONObject("ability").getString("name");
                        abilitiesList.add(abilityName);
                    }

                    JSONArray statsArray = response.getJSONArray("stats");
                    ArrayList<String> statsList = new ArrayList<>();
                    for (int i = 0; i < statsArray.length(); i++) {
                        JSONObject statObject = statsArray.getJSONObject(i);
                        String statName = statObject.getJSONObject("stat").getString("name");
                        int baseStat = statObject.getInt("base_stat");
                        statsList.add(statName + ": " + baseStat);
                    }

                    Pokemon pokemon = new Pokemon(
                            name,
                            height,
                            weight,
                            front_default,
                            back_default,
                            typesList,
                            "", // Not coming from the API
                            abilitiesList,
                            statsList
                    );

                    callback.onPokemonDetailsFetched(pokemon);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(requireContext(), "Failed to fetch Pokémon details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPokemonClick(Pokemon pokemon) {

    }

    // Inner interface for PokemonDetailsCallback
    public interface PokemonDetailsCallback {
        void onPokemonDetailsFetched(Pokemon pokemon);
    }




}
