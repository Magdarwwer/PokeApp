package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;

    public PokemonAdapter(List<Pokemon> pokemonList) {
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
        private ImageView imageViewPokeball;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewFront = itemView.findViewById(R.id.imageViewFront);
            imageViewPokeball = itemView.findViewById(R.id.imageViewPokeball);
        }

        public void bind(Pokemon pokemon) {
            textViewName.setText(pokemon.getName());
            imageViewFront.setImageResource(pokemon.getFrontImage());
            imageViewPokeball.setImageResource(pokemon.isShiny() ? R.drawable.pokeball_shiny : R.drawable.pokeball_normal);
        }
    }
}

