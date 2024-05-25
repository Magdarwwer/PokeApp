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

public class PokemonDetailAdapter extends RecyclerView.Adapter<PokemonDetailAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemonList;
    private Context context;
    private OnPokemonClickListener listener;

    public PokemonDetailAdapter(Context context, ArrayList<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pokemon_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void addPokemonList(ArrayList<Pokemon> newPokemonList) {
        pokemonList.addAll(newPokemonList);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Pokemon> filteredPokemonList) {
        pokemonList = filteredPokemonList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewNameDetail;
        private ImageView imageViewFrontDetail;
        private ImageView imageViewBackDetail;
        private TextView textViewHeight;
        private TextView textViewWeight;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNameDetail = itemView.findViewById(R.id.textViewNameDetail);
            imageViewFrontDetail = itemView.findViewById(R.id.imageViewFrontDetail);
            imageViewBackDetail = itemView.findViewById(R.id.imageViewBackDetail);
            textViewHeight = itemView.findViewById(R.id.textViewHeight);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);

            itemView.setOnClickListener(this);
        }

        public void bind(Pokemon pokemon) {
            textViewNameDetail.setText(pokemon.getName());
            textViewHeight.setText(String.valueOf(pokemon.getHeight()));
            textViewWeight.setText(String.valueOf(pokemon.getWeight()));

            Glide.with(context)
                    .load(pokemon.getFront_default())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewFrontDetail);

            Glide.with(context)
                    .load(pokemon.getBack_default())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewBackDetail);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Pokemon pokemon = pokemonList.get(position);
                listener.onPokemonClick(pokemon, pokemon.getName());
            }
        }
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon, String pokemonName);
    }

    public void setOnPokemonClickListener(OnPokemonClickListener listener) {
        this.listener = listener;
    }
}
