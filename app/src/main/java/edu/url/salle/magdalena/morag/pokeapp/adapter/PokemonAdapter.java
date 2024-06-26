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
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> dataset;
    private Context context;
    private OnPokemonClickListener listener;

    public PokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon, String pokemonName, ArrayList<Pokemon> fullPokemonList);
    }


    public void setOnPokemonClickListener(OnPokemonClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nameTextView.setText(p.getName());

        Glide.with(context)
                .load(getPokemonFrontImageUrl(p.getId()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pictureImageView);
    }

    public String getPokemonFrontImageUrl(int pokemonId) {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png";
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void addPokemonList(List<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public void filterList(List<Pokemon> filteredPokemonList) {
        dataset.clear();
        dataset.addAll(filteredPokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView pictureImageView;
        private TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Pokemon clickedPokemon = dataset.get(position);
                String pokemonName = clickedPokemon.getName();
                listener.onPokemonClick(clickedPokemon, pokemonName, new ArrayList<>(dataset));
            }
        }

    }
}
