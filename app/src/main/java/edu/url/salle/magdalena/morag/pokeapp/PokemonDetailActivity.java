package edu.url.salle.magdalena.morag.pokeapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PokemonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pokemon_detail);

        // Getting data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String frontImage = extras.getString("frontImage");
            String backImage = extras.getString("backImage");
            int height = extras.getInt("height");
            int weight = extras.getInt("weight");
            String types = extras.getString("types");
            String abilities = extras.getString("abilities");
            String stats = extras.getString("stats");

            // Setting data to views
            TextView textViewNameDetail = findViewById(R.id.textViewNameDetail);
            textViewNameDetail.setText(name);

            TextView textViewHeight = findViewById(R.id.textViewHeight);
            textViewHeight.setText("Height: " + height);

            TextView textViewWeight = findViewById(R.id.textViewWeight);
            textViewWeight.setText("Weight: " + weight);

            ImageView imageViewFrontDetail = findViewById(R.id.imageViewFrontDetail);
            Glide.with(this)
                    .load(frontImage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewFrontDetail);

            ImageView imageViewBackDetail = findViewById(R.id.imageViewBackDetail);
            Glide.with(this)
                    .load(backImage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewBackDetail);

            TextView textViewTypesDetail = findViewById(R.id.textViewTypesDetail);
            textViewTypesDetail.setText("Types: " + types);

            TextView textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
            textViewDescriptionDetail.setText("Description: ");

            TextView textViewAbilityDetail = findViewById(R.id.textViewAbilityDetail);
            textViewAbilityDetail.setText("Ability: " + abilities);

            TextView textViewStatsDetail = findViewById(R.id.textViewStatsDetail);
            textViewStatsDetail.setText("Stats: " + stats);
        }
    }
}
