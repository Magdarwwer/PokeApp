package edu.url.salle.magdalena.morag.pokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.url.salle.magdalena.morag.pokeapp.adapter.AbilityAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.StatAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.TypeAdapter;
import edu.url.salle.magdalena.morag.pokeapp.fragment.TrainerFragment;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.Toast;

public class PokemonDetailActivity extends AppCompatActivity {

    private RecyclerView abilitiesRecyclerView;
    private RecyclerView statsRecyclerView;
    private RecyclerView typesRecyclerView;
    private AbilityAdapter abilityAdapter;
    private StatAdapter statAdapter;
    private TypeAdapter typeAdapter;
    private TextView nameTextView;
    private ImageView frontImageView;
    private ImageView backImageView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView descriptionTextView;
    private Button catchButton;

    private Trainer currentTrainer;
    private TrainerFragment trainerFragment;

    public void setTrainerFragment(TrainerFragment fragment) {
        this.trainerFragment = fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pokemon_detail);

        abilitiesRecyclerView = findViewById(R.id.recyclerViewAbilities);
        statsRecyclerView = findViewById(R.id.recyclerViewStats);
        typesRecyclerView = findViewById(R.id.recyclerViewTypes);
        descriptionTextView = findViewById(R.id.textViewDescription);
        catchButton = findViewById(R.id.buttonCatchPokemon);

        abilityAdapter = new AbilityAdapter();
        statAdapter = new StatAdapter();
        typeAdapter = new TypeAdapter();

        abilitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        statsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        typesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        abilitiesRecyclerView.setAdapter(abilityAdapter);
        statsRecyclerView.setAdapter(statAdapter);
        typesRecyclerView.setAdapter(typeAdapter);

        nameTextView = findViewById(R.id.textViewNameDetail);
        frontImageView = findViewById(R.id.imageViewFrontDetail);
        backImageView = findViewById(R.id.imageViewBackDetail);
        heightTextView = findViewById(R.id.textViewHeight);
        weightTextView = findViewById(R.id.textViewWeight);

        Button captureButton = findViewById(R.id.buttonCapturePokemon);
        captureButton.setOnClickListener(v -> {
            if (currentTrainer != null) {
                Pokemon pokemonToCapture = getCurrentPokemonToInteract();
                String pokeballType = "Pokeball";
                trainerFragment.performTransaction(0, "capture", pokemonToCapture, pokeballType);
            } else {
                Toast.makeText(this, "TrainerFragment is not set", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("pokemon") && intent.hasExtra("fullPokemonList")) {
            Pokemon pokemon = intent.getParcelableExtra("pokemon");
            Bundle bundle = intent.getExtras();
            if (pokemon != null && bundle != null) {
                ArrayList<Pokemon> fullPokemonList = (ArrayList<Pokemon>) bundle.getSerializable("fullPokemonList");
                if (fullPokemonList != null) {
                    Pokemon selectedPokemon = fullPokemonList.stream().filter(p -> p.getId() == pokemon.getId()).findFirst().orElse(null);

                    if (selectedPokemon != null) {
                        nameTextView.setText(selectedPokemon.getName().toUpperCase());
                        heightTextView.setText("Height: " + selectedPokemon.getHeight());
                        weightTextView.setText("Weight: " + selectedPokemon.getWeight());
                        descriptionTextView.setText(selectedPokemon.getDescription());

                        // Load Pokemon images
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

                        abilityAdapter.setAbilities(selectedPokemon.getAbilitiesList());
                        statAdapter.setStats(selectedPokemon.getStatsList());
                        typeAdapter.setTypes(selectedPokemon.getTypesList());

                        abilityAdapter.notifyDataSetChanged();
                        statAdapter.notifyDataSetChanged();
                        typeAdapter.notifyDataSetChanged();

                        catchButton.setOnClickListener(v -> showCatchDialog(selectedPokemon));
                    }
                }
            }
        }
        currentTrainer = getCurrentTrainer();
    }

    public Pokemon getCurrentPokemonToInteract() {
        if (currentTrainer != null && currentTrainer.getCapturedPokemons() != null && !currentTrainer.getCapturedPokemons().isEmpty()) {
            ArrayList<Pokemon> capturedPokemons = currentTrainer.getCapturedPokemons();

            Random random = new Random();
            int index = random.nextInt(capturedPokemons.size());

            return capturedPokemons.get(index);
        } else {

            return null;
        }
    }

    private Trainer getCurrentTrainer() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String trainerName = sharedPreferences.getString("trainer_name", null);
        if (trainerName != null) {
            return new Trainer(1, trainerName, 1000, new ArrayList<>(), new ArrayList<>());
        }
        return null;
    }

    private void showCatchDialog(Pokemon pokemon) {
        List<String> items = currentTrainer.getItems();
        String[] pokeballs = items.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Pokéball");
        builder.setItems(pokeballs, (dialog, which) -> {
            String selectedBall = pokeballs[which];
            catchPokemon(pokemon, selectedBall);
        });
        builder.show();
    }


    private void catchPokemon(Pokemon pokemon, String pokeball) {
        Toast.makeText(this, "Caught " + pokemon.getName() + " with a " + pokeball + "!", Toast.LENGTH_SHORT).show();
    }
}
