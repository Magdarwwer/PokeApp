package edu.url.salle.magdalena.morag.pokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;


public class PokemonDetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView frontImageView;
    private ImageView backImageView;
    private TextView heightTextView;
    private TextView weightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pokemon_detail);

        nameTextView = findViewById(R.id.textViewNameDetail);
        frontImageView = findViewById(R.id.imageViewFrontDetail);
        backImageView = findViewById(R.id.imageViewBackDetail);
        heightTextView = findViewById(R.id.textViewHeight);
        weightTextView = findViewById(R.id.textViewWeight);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("pokemon") && intent.hasExtra("fullPokemonList")) {
            Pokemon pokemon = intent.getParcelableExtra("pokemon");
            Bundle bundle = intent.getExtras();
            if (pokemon != null && bundle != null) {
                ArrayList<Pokemon> fullPokemonList = (ArrayList<Pokemon>) bundle.getSerializable("fullPokemonList");
                if (fullPokemonList != null) {
                    Pokemon selectedPokemon = null;
                    for (Pokemon p : fullPokemonList) {
                        if (p.getId() == pokemon.getId()) {
                            selectedPokemon = p;
                            break;
                        }
                    }

                    if (selectedPokemon != null) {
                        nameTextView.setText(selectedPokemon.getName().toUpperCase());
                        heightTextView.setText("Height: " + selectedPokemon.getHeight());
                        weightTextView.setText("Weight: " + selectedPokemon.getWeight());

                        Glide.with(this)
                                .load(selectedPokemon.getFront_default())
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(frontImageView);

                        Glide.with(this)
                                .load(selectedPokemon.getBack_default())
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(backImageView);
                    }
                }
            }
        }
    }
}