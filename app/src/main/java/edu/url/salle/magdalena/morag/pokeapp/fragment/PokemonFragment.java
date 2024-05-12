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

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonListResponse;
import edu.url.salle.magdalena.morag.pokeapp.service.PokeApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonFragment extends Fragment {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemonList;
    private ArrayList<String> pokemonNames;

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokedex, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewPokemons);
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(pokemonList);
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



    private RecyclerView fetchData() {
        PokeApiService.getPokeApi(BASE_URL).getAllPokemon().enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonListResponse> call, @NonNull Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();
                    if (pokemonListResponse != null) {
                        ArrayList<Pokemon> newPokemonList = pokemonListResponse.getResults();
                        for (Pokemon pokemon : newPokemonList) {
                            String pokemonName = pokemon.getName();
                            Log.d("name", pokemonName);
                            pokemonNames.add(pokemonName);
                        }
                        pokemonList.addAll(newPokemonList);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Pokémon data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonListResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return recyclerView;
    }
}
