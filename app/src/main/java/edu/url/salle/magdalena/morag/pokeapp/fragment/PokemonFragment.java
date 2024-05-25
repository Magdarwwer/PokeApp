package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import cz.msebera.android.httpclient.Header;
import edu.url.salle.magdalena.morag.pokeapp.PokemonDetailActivity;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonAdapter;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonFragment extends Fragment implements PokemonAdapter.OnPokemonClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Pokemon> fullPokemonList;

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
        for (Pokemon pokemon : fullPokemonList) {
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
                    ArrayList<Pokemon> fetchedPokemonList = new ArrayList<>();
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject pokemonJson = results.getJSONObject(i);
                        String pokemonName = pokemonJson.getString("name");
                        int pokemonId = i + 1;
                        Pokemon pokemon = new Pokemon(pokemonId, pokemonName);
                        fetchedPokemonList.add(pokemon);

                        fetchPokemonDetails(pokemon, BASE_URL + "pokemon/" + pokemonName);
                    }

                    fullPokemonList = fetchedPokemonList;
                    adapter.addPokemonList(fullPokemonList);
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

    public void fetchPokemonDetails(Pokemon pokemon, String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String name = response.getString("name");
                    int height = response.getInt("height");
                    int weight = response.getInt("weight");

                    // Fetching sprites
                    JSONObject sprites = response.getJSONObject("sprites");
                    String front_default = sprites.getString("front_default");
                    String back_default = sprites.getString("back_default");

                    // Fetching types
                    JSONArray typesArray = response.getJSONArray("types");
                    ArrayList<String> typesList = new ArrayList<>();
                    for (int i = 0; i < typesArray.length(); i++) {
                        JSONObject typeObject = typesArray.getJSONObject(i);
                        String typeName = typeObject.getJSONObject("type").getString("name");
                        typesList.add(typeName);
                    }

                    // Fetching abilities
                    JSONArray abilitiesArray = response.getJSONArray("abilities");
                    ArrayList<String> abilitiesList = new ArrayList<>();
                    for (int i = 0; i < abilitiesArray.length(); i++) {
                        JSONObject abilityObject = abilitiesArray.getJSONObject(i);
                        String abilityName = abilityObject.getJSONObject("ability").getString("name");
                        abilitiesList.add(abilityName);
                    }

                    // Fetching stats
                    JSONArray statsArray = response.getJSONArray("stats");
                    ArrayList<String> statsList = new ArrayList<>();
                    for (int i = 0; i < statsArray.length(); i++) {
                        JSONObject statObject = statsArray.getJSONObject(i);
                        String statName = statObject.getJSONObject("stat").getString("name");
                        int baseStat = statObject.getInt("base_stat");
                        statsList.add(statName + ": " + baseStat);
                    }

                    pokemon.setName(name);
                    pokemon.setFront_default(front_default);
                    pokemon.setBack_default(back_default);
                    pokemon.setHeight(height);
                    pokemon.setWeight(weight);
                    pokemon.setTypesList(typesList);
                    pokemon.setAbilitiesList(abilitiesList);
                    pokemon.setStatsList(statsList);

                    updatePokemonInList(pokemon);

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

    private void updatePokemonInList(Pokemon updatedPokemon) {
        for (int i = 0; i < pokemonList.size(); i++) {
            if (pokemonList.get(i).getId() == updatedPokemon.getId()) {
                pokemonList.set(i, updatedPokemon);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onPokemonClick(Pokemon pokemon, String pokemonName, ArrayList<Pokemon> fullPokemonList) {
        Intent intent = new Intent(requireContext(), PokemonDetailActivity.class);
        intent.putExtra("pokemon", pokemon);
        intent.putExtra("name", pokemon.getName());
        intent.putExtra("height", pokemon.getHeight());
        intent.putExtra("weight", pokemon.getWeight());
        intent.putExtra("front_default", pokemon.getFront_default());
        intent.putExtra("back_default", pokemon.getBack_default());

        if (fullPokemonList != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("fullPokemonList", fullPokemonList);
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }

}
