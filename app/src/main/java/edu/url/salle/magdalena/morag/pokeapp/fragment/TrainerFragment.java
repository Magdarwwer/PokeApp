package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.url.salle.magdalena.morag.pokeapp.PokemonDetailActivity;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.adapter.CapturedPokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.ItemAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokeball;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.model.TrainerManager;

public class TrainerFragment extends Fragment implements PokemonDetailActivity.OnPokemonCaughtListener {

    private static final String PREF_NAME = "TrainerData";
    private static final String KEY_TRAINER_NAME = "trainerName";
    private static final String KEY_TRAINER_MONEY = "trainerMoney";
    private static final String KEY_TRAINER_ITEMS = "trainerItems";
    private static final String KEY_TRAINER_POKEDEX = "trainerPokedex";

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private RecyclerView recyclerViewItems;
    private RecyclerView recyclerViewCapturedPokemons;
    private Button buttonChangeName;
    private Button buttonReleasePokemon;
    private TrainerManager trainerManager;
    private SharedPreferences sharedPreferences;
    private ItemAdapter itemAdapter;
    private CapturedPokemonAdapter adapter;
    private PokemonAdapter pokemonAdapter;

    public static TrainerFragment getInstance() {
        return new TrainerFragment();
    }


    public TrainerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainerManager = TrainerManager.getInstance();
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = rootView.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = rootView.findViewById(R.id.textViewTrainerMoney);
        recyclerViewItems = rootView.findViewById(R.id.recyclerViewItems);
        recyclerViewCapturedPokemons = rootView.findViewById(R.id.recyclerViewCapturedPokemons);
        buttonChangeName = rootView.findViewById(R.id.buttonChangeName);
        buttonReleasePokemon = rootView.findViewById(R.id.buttonReleasePokemon);



        itemAdapter = new ItemAdapter(getContext());
        pokemonAdapter = new PokemonAdapter(getContext());
        adapter = new CapturedPokemonAdapter(new ArrayList<>(), getContext(), pokemonAdapter);

        LinearLayoutManager itemsLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewItems.setLayoutManager(itemsLayoutManager);

        LinearLayoutManager capturedPokemonsLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCapturedPokemons.setLayoutManager(capturedPokemonsLayoutManager);


        buttonChangeName.setOnClickListener(v -> showChangeNameDialog());
        buttonReleasePokemon.setOnClickListener(v -> showReleasePokemonDialog());

        loadAndDisplayTrainerData();
        itemAdapter.notifyDataSetChanged();
        pokemonAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void loadAndDisplayTrainerData() {
        String trainerName = sharedPreferences.getString(KEY_TRAINER_NAME, "");
        int trainerMoney = sharedPreferences.getInt(KEY_TRAINER_MONEY, 0);
        Set<String> itemSet = sharedPreferences.getStringSet(KEY_TRAINER_ITEMS, new HashSet<>());
        Set<String> pokemonSet = sharedPreferences.getStringSet(KEY_TRAINER_POKEDEX, new HashSet<>());

        Trainer trainer = trainerManager.loadTrainerData(trainerName, trainerMoney, itemSet, pokemonSet);
        if (trainer != null) {
            textViewTrainerName.setText(trainer.getName());
            textViewTrainerMoney.setText(getString(R.string.money_format, trainer.getMoney()));
            itemAdapter = new ItemAdapter(getContext());
            recyclerViewItems.setAdapter(itemAdapter);
            pokemonAdapter = new PokemonAdapter(getContext());
            recyclerViewCapturedPokemons.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Trainer data not available", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Trainer activeTrainer = trainerManager.getActiveTrainer();
        saveTrainerData(activeTrainer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAndDisplayTrainerData();
    }



    public void saveTrainerData(Trainer trainer) {
        if (trainer != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_TRAINER_NAME, trainer.getName());
            editor.putInt(KEY_TRAINER_MONEY, trainer.getMoney());
            editor.putStringSet(KEY_TRAINER_ITEMS, new HashSet<>(trainer.getItems()));

            Set<String> pokemonSet = new HashSet<>();
            for (Pokemon pokemon : trainer.getPokedex()) {
                pokemonSet.add(pokemon.toJson());
            }
            editor.putStringSet(KEY_TRAINER_POKEDEX, pokemonSet);

            editor.apply();
        }
    }

    public void saveTrainerData() {
        Trainer activeTrainer = trainerManager.getActiveTrainer();
        saveTrainerData(activeTrainer);
    }



    private void showChangeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change Trainer's Name");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                textViewTrainerName.setText(newName);
                Trainer activeTrainer = trainerManager.getActiveTrainer();
                if (activeTrainer != null) {
                    activeTrainer.setName(newName);
                    saveTrainerData(activeTrainer);
                }
            } else {
                Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showReleasePokemonDialog() {
        Trainer activeTrainer = trainerManager.getActiveTrainer();
        if (activeTrainer != null) {
            ArrayList<Pokemon> pokedex = activeTrainer.getPokedex();
            if (pokedex.isEmpty()) {
                Toast.makeText(getContext(), "No Pokémon to release", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select Pokémon to Release");

            String[] pokemonNames = new String[pokedex.size()];
            for (int i = 0; i < pokedex.size(); i++) {
                pokemonNames[i] = pokedex.get(i).getName();
            }

            builder.setItems(pokemonNames, (dialog, which) -> {
                Pokemon releasedPokemon = pokedex.remove(which);
                adapter.updateCapturedPokemons(pokedex);
                saveTrainerData(activeTrainer);
                Toast.makeText(getContext(), "Released " + releasedPokemon.getName(), Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        } else {
            Toast.makeText(getContext(), "Trainer data not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Trainer activeTrainer = trainerManager.getActiveTrainer();
        saveTrainerData(activeTrainer);
    }

    @Override
    public void onPokemonCaught(Pokemon pokemon, Pokeball pokeball) {
        if (pokemon != null) {
            Trainer activeTrainer = TrainerManager.getInstance().getActiveTrainer();
            if (activeTrainer != null) {
                pokemon.setPokeball(pokeball);
                pokemon.setCaught(true);
                activeTrainer.getPokedex().add(pokemon);
                pokemon.setCaught(true);
                saveTrainerData(activeTrainer);
                adapter.updateCapturedPokemons(activeTrainer.getPokedex());
                Toast.makeText(getContext(), pokemon.getName() + " was caught with a " + pokeball.getType() + "!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Failed to catch Pokémon", Toast.LENGTH_SHORT).show();
        }
    }

}
