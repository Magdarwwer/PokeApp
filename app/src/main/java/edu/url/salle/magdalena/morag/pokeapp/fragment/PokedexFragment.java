package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.MainActivity;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokedexFragment extends Fragment {

    private RecyclerView recyclerViewPokemons;
    private List<Pokemon> pokemonList;
    private PokemonAdapter pokemonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        recyclerViewPokemons = view.findViewById(R.id.recyclerViewPokemons);
        recyclerViewPokemons.setLayoutManager(new LinearLayoutManager(getActivity()));

        pokemonList = new ArrayList<>();
        pokemonAdapter = new PokemonAdapter(pokemonList);
        recyclerViewPokemons.setAdapter(pokemonAdapter);

        ((MainActivity) getActivity()).getAllPokemon();

        return view;
    }

    // Update RecyclerView with the fetched pokemon list
    public void updatePokemonList(List<Pokemon> pokemonList) {
        this.pokemonList.clear();
        this.pokemonList.addAll(pokemonList);
        pokemonAdapter.notifyDataSetChanged();
    }
}
