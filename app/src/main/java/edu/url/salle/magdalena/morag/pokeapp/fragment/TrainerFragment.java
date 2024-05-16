package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.app.AlertDialog;
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

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.R;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.service.PokeApiService;

public class TrainerFragment extends Fragment {

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private TextView textViewItems;
    private TextView textViewCapturedPokemons;
    private List<Trainer> trainers;
    private Trainer currentTrainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = root.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = root.findViewById(R.id.textViewTrainerMoney);
        textViewItems = root.findViewById(R.id.textViewItems);
        textViewCapturedPokemons = root.findViewById(R.id.textViewCapturedPokemons);

        getTrainers();

        Button openDialogButton = root.findViewById(R.id.buttonOpenDialog);
        openDialogButton.setOnClickListener(v -> showChangeNameDialog());

        Button searchButton = root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(v -> showSearchDialog());

        return root;
    }

    private void searchTrainer(String name) {
        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(name)) {
                currentTrainer = trainer;
                updateTrainerInfo(currentTrainer);
                return;
            }
        }
        Toast.makeText(requireContext(), "Trainer not found", Toast.LENGTH_SHORT).show();
    }

    public List<Trainer> getTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer("Ash", 1000, new ArrayList<>(), new ArrayList<>()));
        trainers.add(new Trainer("Misty", 1500, new ArrayList<>(), new ArrayList<>()));
        trainers.add(new Trainer("Brock", 1200, new ArrayList<>(), new ArrayList<>()));

        List<List<String>> items = getItems();
        for (int i = 0; i < trainers.size(); i++) {
            Trainer trainer = trainers.get(i);
            List<String> trainerItems = items.get(i);
            trainer.getItems().addAll(trainerItems);
        }

        return trainers;
    }

    public List<List<String>> getItems() {
        List<List<String>> allItems = new ArrayList<>();
        List<String> ashItems = Arrays.asList("Potion", "Revive", "Great Ball");
        List<String> mistyItems = Arrays.asList("Potion", "Super Potion", "Ultra Ball");
        List<String> brockItems = Arrays.asList("Potion", "Max Potion", "Master Ball");

        allItems.add(ashItems);
        allItems.add(mistyItems);
        allItems.add(brockItems);

        return allItems;
    }


    // Method to update Trainer's information
    private void updateTrainerInfo(Trainer trainer) {
        textViewTrainerName.setText(trainer.getName());
        textViewTrainerMoney.setText(getString(R.string.money_format, trainer.getMoney()));
        textViewItems.setText(TextUtils.join(", ", trainer.getItems()));
        //textViewCapturedPokemons.setText(TextUtils.join(", ", trainer.getCapturedPokemons()));
    }

    // Method to update Trainer's name
    private void updateTrainerName(String newName) {
        if (currentTrainer != null) {
            currentTrainer.setName(newName);
            updateTrainerInfo(currentTrainer);
        }
    }

    // Method to show dialog for changing Trainer's name
    private void showChangeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Trainer's Name");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            updateTrainerName(newName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to show dialog for searching Trainer
    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Search Trainer");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Search", (dialog, which) -> {
            String searchName = input.getText().toString().trim();
            searchTrainer(searchName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to fetch Pokemon details and add it to the Trainer
    public void addPokemonToTrainer(Trainer trainer, Pokemon pokemon) {
        PokemonFragment pokemonFragment = new PokemonFragment();
        pokemonFragment.fetchPokemonDetails(pokemon, new PokemonFragment.PokemonDetailsCallback() {
            @Override
            public void onPokemonDetailsFetched(Pokemon pokemon) {
                // Update the trainer object with the fetched PokemonDetails
                if (trainer != null && pokemon != null) {
                    trainer.addPokemon(pokemon);
                    updateTrainerInfo(trainer);
                    showToastMessage("Added " + pokemon.getName() + " to " + trainer.getName());
                }
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Method to handle adding a Pokemon to the Trainer
    private void onAddPokemonButtonClick(Trainer trainer, Pokemon pokemon) {
        // Call this method when you want to add a Pokemon to a Trainer
        addPokemonToTrainer(trainer, pokemon);
    }
}
