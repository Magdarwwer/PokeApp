package edu.url.salle.magdalena.morag.pokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonDetailAdapter;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;


public class PokemonDetailActivity extends AppCompatActivity {

    /*private TextView nameTextView;
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
        if (intent != null && intent.hasExtra("pokemon")) {
            Pokemon pokemon = intent.getParcelableExtra("pokemon");
            if (pokemon != null) {
                nameTextView.setText(pokemon.getName());
                heightTextView.setText(pokemon.getHeight());
                weightTextView.setText(pokemon.getWeight());

                Glide.with(this)
                        .load(pokemon.getFront_default())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(frontImageView);

                Glide.with(this)
                        .load(pokemon.getBack_default())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(backImageView);
            }
        }
    }*/
}