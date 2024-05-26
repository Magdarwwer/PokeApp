package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.CapturedPokemonViewHolder> {

    private ArrayList<Pokemon> capturedPokemons;
    private Context context;
    private final PokemonAdapter pokemonAdapter;

    public CapturedPokemonAdapter(ArrayList<Pokemon> capturedPokemons, Context context, PokemonAdapter pokemonAdapter) {
        this.capturedPokemons = capturedPokemons;
        this.context = context;
        this.pokemonAdapter = pokemonAdapter;
    }
    public void updateCapturedPokemons(ArrayList<Pokemon> capturedPokemons) {
        this.capturedPokemons = capturedPokemons;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CapturedPokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.captured_pokemon_item, parent, false);
        return new CapturedPokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturedPokemonViewHolder holder, int position) {
        Pokemon pokemon = capturedPokemons.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return capturedPokemons.size();
    }

    public class CapturedPokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView pokemonNameTextView;
        private ImageView pokemonImageView;
        private ImageView pokeballImageView;

        public CapturedPokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonNameTextView = itemView.findViewById(R.id.textViewPokemonName);
            pokemonImageView = itemView.findViewById(R.id.imageViewPokemon);
            pokeballImageView = itemView.findViewById(R.id.imageViewPokeball);
        }

        public void bind(Pokemon pokemon) {
            if (pokemon != null) {
                pokemonNameTextView.setText(pokemon.getName());
                pokeballImageView.setImageResource(getPokeballImageResId(pokemon.getPokeballType()));

                Glide.with(itemView.getContext())
                        .load(pokemonAdapter.getPokemonFrontImageUrl(pokemon.getId()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(pokemonImageView);
            }
        }

        private int getPokeballImageResId(String pokeballType) {
            switch (pokeballType) {
                case "Pokeball":
                    return R.mipmap.pokeball;
                case "Superball":
                    return R.mipmap.superball;
                case "Ultraball":
                    return R.mipmap.ultraball;
                case "Masterball":
                    return R.mipmap.masterball;
                default:
                    return R.mipmap.gamepoke;
            }
        }
    }
}