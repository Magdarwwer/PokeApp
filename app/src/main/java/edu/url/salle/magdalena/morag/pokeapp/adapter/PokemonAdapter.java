package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Random;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Pokemon> pokemonList;
    private Random random = new Random();
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isSearchActive = false;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public PokemonAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_pokedex, parent, false);
            return new PokemonViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PokemonViewHolder) {
            Pokemon pokemon = pokemonList.get(position);
            ((PokemonViewHolder) holder).bind(pokemon);
        } else if (holder instanceof LoadingViewHolder) {
            // Error
        }
    }
    @Override
    public int getItemCount() {
        if (isSearchActive) {
            return pokemonList.size();
        } else {
            return Math.min(pokemonList.size(), 15);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == pokemonList.size() - 1 && isLoading ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setLoading() {
        isLoading = true;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

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
            imageViewPokeball.setImageResource(R.drawable.pokeball_normal);

            int randomValue = random.nextInt(500);
            if (randomValue == 0) {
                imageViewPokeball.setImageResource(R.drawable.pokeball_shiny);
            }
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
