package edu.url.salle.magdalena.morag.pokeapp.fragment;

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
import androidx.annotation.Nullable;
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
import edu.url.salle.magdalena.morag.pokeapp.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonFragment extends Fragment {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemonList;

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokedex, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

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

        fetchData();

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

    private void fetchData() {
        AsyncHttpClient client = new AsyncHttpClient();
        fetchPokemonRecursive(client, BASE_URL + "pokemon");
    }

    private void fetchPokemonRecursive(AsyncHttpClient client, String url) {
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject pokemonJson = results.getJSONObject(i);
                        String pokemonName = pokemonJson.getString("name");
                        String pokemonUrl = pokemonJson.getString("url");
                        Pokemon pokemon = new Pokemon(pokemonName, pokemonUrl);
                        pokemonList.add(pokemon);
                    }
                    adapter.notifyDataSetChanged();

                    // Check if there are more Pokémon to fetch
                    if (!response.isNull("next")) {
                        String nextUrl = response.getString("next");
                        fetchPokemonRecursive(client, nextUrl);
                    }
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




}