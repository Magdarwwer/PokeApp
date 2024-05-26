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
import edu.url.salle.magdalena.morag.pokeapp.model.Pokeball;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.CapturedPokemonViewHolder> {

    private ArrayList<Pokemon> capturedPokemons;
    private Context context;

    public CapturedPokemonAdapter(ArrayList<Pokemon> capturedPokemons, Context context) {
        this.capturedPokemons = capturedPokemons;
        this.context = context;
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

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.capturedPokemons = pokemons;
        notifyDataSetChanged();
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

                Pokeball pokeball = pokemon.getPokeball();
                if (pokeball != null) {
                    String pokeballType = pokeball.getType();
                    pokeballImageView.setImageResource(getPokeballImageResId(pokeballType));
                } else {
                    pokeballImageView.setImageResource(R.mipmap.gamepoke);
                }

                Glide.with(itemView.getContext())
                        .load(getPokemonFrontImageUrl(pokemon.getId()))
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

        private String getPokemonFrontImageUrl(int pokemonId) {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png";
        }
    }
}
