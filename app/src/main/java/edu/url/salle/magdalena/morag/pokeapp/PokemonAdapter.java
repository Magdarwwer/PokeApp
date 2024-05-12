package edu.url.salle.magdalena.morag.pokeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private ArrayList<Pokemon> pokemonList;

    public PokemonAdapter(ArrayList<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private ImageView imageViewFront;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewFront = itemView.findViewById(R.id.imageViewFront);
        }

        public void bind(Pokemon pokemon) {
            textViewName.setText(pokemon.getName());
            imageViewFront.setImageResource(pokemon.getFrontImage());
        }
    }
}
