package edu.url.salle.magdalena.morag.pokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.adapter.AbilityAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.StatAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.TypeAdapter;
import edu.url.salle.magdalena.morag.pokeapp.fragment.TrainerFragment;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokeball;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.model.TrainerManager;
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
    private Button catchPokemonButton;
    private Pokemon selectedPokemon;
    private Trainer currentTrainer;
    private TrainerFragment trainerFragment;

    public PokemonDetailActivity(){
        trainerFragment = new TrainerFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pokemon_detail);


        abilitiesRecyclerView = findViewById(R.id.recyclerViewAbilities);
        statsRecyclerView = findViewById(R.id.recyclerViewStats);
        typesRecyclerView = findViewById(R.id.recyclerViewTypes);
        descriptionTextView = findViewById(R.id.textViewDescription);
        catchPokemonButton = findViewById(R.id.buttonCatchPokemon);
        nameTextView = findViewById(R.id.textViewNameDetail);
        frontImageView = findViewById(R.id.imageViewFrontDetail);
        backImageView = findViewById(R.id.imageViewBackDetail);
        heightTextView = findViewById(R.id.textViewHeight);
        weightTextView = findViewById(R.id.textViewWeight);
        TextView caughtTextView = findViewById(R.id.textViewCaught);


        abilityAdapter = new AbilityAdapter();
        statAdapter = new StatAdapter();
        typeAdapter = new TypeAdapter();

        abilitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        statsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        typesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        abilitiesRecyclerView.setAdapter(abilityAdapter);
        statsRecyclerView.setAdapter(statAdapter);
        typesRecyclerView.setAdapter(typeAdapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("pokemon") && intent.hasExtra("fullPokemonList")) {
            Pokemon pokemon = intent.getParcelableExtra("pokemon");
            Bundle bundle = intent.getExtras();
            currentTrainer = (Trainer) intent.getSerializableExtra("trainer");
            if (pokemon != null && bundle != null) {
                ArrayList<Pokemon> fullPokemonList = (ArrayList<Pokemon>) bundle.getSerializable("fullPokemonList");
                if (fullPokemonList != null) {
                    selectedPokemon = null;
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
                        descriptionTextView.setText(selectedPokemon.getDescription());

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

                        if (selectedPokemon.isCaught()) {
                            caughtTextView.setText("Caught: Yes");
                        } else {
                            caughtTextView.setText("Caught: No");
                        }
                    }
                }
            }
        }


        catchPokemonButton.setOnClickListener(v -> {
            Pokeball pokeball = new Pokeball("Pokeball");
            onPokemonCaughtWithPokeball(selectedPokemon, pokeball);
        });
    }


    public interface OnPokemonCaughtListener {
        void onPokemonCaught(Pokemon pokemon, Pokeball pokeball);
    }

    public void onPokemonCaughtWithPokeball(Pokemon pokemon, Pokeball pokeball) {
        if (pokemon != null) {
            Trainer activeTrainer = TrainerManager.getInstance().getActiveTrainer();
            if (activeTrainer != null) {
                double captureProbability = pokeball.getCaptureProbability(pokemon.getType());
                if (Math.random() < captureProbability) {
                    activeTrainer.capturePokemon(pokemon, pokeball);
                    pokemon.setCaught(true);
                    trainerFragment.saveTrainerData(activeTrainer);
                    Toast.makeText(this, pokemon.getName() + " was caught!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Pokemon escaped from the Pokeball!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Failed to catch Pokémon", Toast.LENGTH_SHORT).show();
        }
    }



}
