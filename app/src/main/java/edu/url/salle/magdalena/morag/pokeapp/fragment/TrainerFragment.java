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

public class TrainerFragment extends Fragment {

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private TextView textViewItems;
    private TextView textViewCapturedPokemons;
    private List<Trainer> trainers;
    private Trainer currentTrainer;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = root.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = root.findViewById(R.id.textViewTrainerMoney);
        textViewItems = root.findViewById(R.id.textViewItems);
        textViewCapturedPokemons = root.findViewById(R.id.textViewCapturedPokemons);

        List<String> ashItems = Arrays.asList("Potion", "Revive", "Great Ball");
        List<String> mistyItems = Arrays.asList("Potion", "Super Potion", "Ultra Ball");
        List<String> brockItems = Arrays.asList("Potion", "Max Potion", "Master Ball");

        trainers = new ArrayList<>();
        Trainer ash = new Trainer(1, "Ash", 1000, new ArrayList<>(), new ArrayList<>());
        Trainer misty = new Trainer(2, "Misty", 1500, new ArrayList<>(), new ArrayList<>());
        Trainer brock = new Trainer(3, "Brock", 1200, new ArrayList<>(), new ArrayList<>());

        trainers.add(ash);
        trainers.add(misty);
        trainers.add(brock);

        List<List<String>> items = new ArrayList<>();
        items.add(ashItems);
        items.add(mistyItems);
        items.add(brockItems);

        for (int i = 0; i < trainers.size(); i++) {
            Trainer trainer = trainers.get(i);
            List<String> trainerItems = items.get(i);
            trainer.getItems().addAll(trainerItems);
        }

        Button openDialogButton = root.findViewById(R.id.buttonOpenDialog);
        openDialogButton.setOnClickListener(v -> showChangeNameDialog());

        Button searchButton = root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(v -> showSearchDialog());

        return root;
    }


    private void searchTrainer(String name) {
        if (trainers == null || trainers.isEmpty()) {
            Toast.makeText(requireContext(), "No trainers available", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(name)) {
                currentTrainer = trainer;
                updateTrainerInfo(currentTrainer);
                //showButtons();
                return;
            }
        }
        Toast.makeText(requireContext(), "Trainer not found", Toast.LENGTH_SHORT).show();
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
   /* public void addPokemonToTrainer(Trainer trainer, Pokemon pokemon) {
        PokemonFragment pokemonFragment = new PokemonFragment();
        pokemonFragment.fetchPokemonDetails(pokemon, new PokemonFragment.PokemonDetailsCallback() {
            @Override
            public void onPokemonDetailsFetched(Pokemon pokemon) {
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

    private void onAddPokemonButtonClick(Trainer trainer, Pokemon pokemon) {
        addPokemonToTrainer(trainer, pokemon);
    }

    private void showButtons() {
        ViewGroup layout = root.findViewById(R.id.buttonShowOrNotShow);
        layout.removeAllViews();

        if (currentTrainer == null) {
            return;
        }

        // If there are less than 6 pokemons, show "Add Pokemon" button
        if (currentTrainer.getCapturedPokemons().size() < 6) {
            Button addButton = new Button(requireContext());
            addButton.setText("Add Pokemon");
            addButton.setOnClickListener(v -> showAddPokemonDialog(currentTrainer));
            layout.addView(addButton);
        } else {
            // If there are 6 or more pokemons, show "Remove Pokemon" button
            Button removeButton = new Button(requireContext());
            removeButton.setText("Remove Pokemon");
            removeButton.setOnClickListener(v -> showRemovePokemonDialog(currentTrainer));
            layout.addView(removeButton);
        }
    }

    private void showAddPokemonDialog(Trainer trainer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Pokemon");

        builder.setPositiveButton("OK", (dialog, which) -> {

            showToastMessage("Add Pokemon logic to be implemented");
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showRemovePokemonDialog(Trainer trainer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove Pokemon");

        List<Pokemon> capturedPokemons = trainer.getCapturedPokemons();

        String[] pokemonNames = new String[capturedPokemons.size()];
        for (int i = 0; i < capturedPokemons.size(); i++) {
            pokemonNames[i] = capturedPokemons.get(i).getName();
        }

        builder.setItems(pokemonNames, (dialog, which) -> {
            Pokemon removedPokemon = capturedPokemons.remove(which);
            updateTrainerInfo(trainer);
            showToastMessage("Removed " + removedPokemon.getName() + " from " + trainer.getName());
            showButtons();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }*/


}
