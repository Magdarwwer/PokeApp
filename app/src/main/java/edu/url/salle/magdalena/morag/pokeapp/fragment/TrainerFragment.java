package edu.url.salle.magdalena.morag.pokeapp.fragment;

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

import androidx.fragment.app.Fragment;

import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.util.FileHandler;

public class TrainerFragment extends Fragment {

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private TextView textViewItems;
    private TextView textViewCapturedPokemons;
    private List<Trainer> trainers;
    private Trainer currentTrainer;
    private View root;
    private FileHandler fileHandler;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = root.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = root.findViewById(R.id.textViewTrainerMoney);
        textViewItems = root.findViewById(R.id.textViewItems);
        textViewCapturedPokemons = root.findViewById(R.id.textViewCapturedPokemons);
        fileHandler = new FileHandler(requireContext());
        sharedPreferences = requireContext().getSharedPreferences("TrainerPrefs", Context.MODE_PRIVATE);

        // Load trainers from file
        trainers = fileHandler.loadTrainers();

        // Set up buttons
        Button openDialogButton = root.findViewById(R.id.buttonOpenDialog);
        openDialogButton.setOnClickListener(v -> showChangeNameDialog());

        Button searchButton = root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(v -> showSearchDialog());

        // Check if there is a searched trainer saved in SharedPreferences
        if (sharedPreferences.contains("searched_trainer_id")) {
            int searchedTrainerId = sharedPreferences.getInt("searched_trainer_id", -1);
            for (Trainer trainer : trainers) {
                if (trainer.getId() == searchedTrainerId) {
                    currentTrainer = trainer;
                    updateTrainerInfo(currentTrainer);
                    break;
                }
            }
        }

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

                // Save searched trainer ID to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("searched_trainer_id", currentTrainer.getId());
                editor.apply();

                return;
            }
        }
        Toast.makeText(requireContext(), "Trainer not found", Toast.LENGTH_SHORT).show();
    }

    private void updateTrainerInfo(Trainer trainer) {
        textViewTrainerName.setText(trainer.getName());
        textViewTrainerMoney.setText(getString(R.string.money_format, trainer.getMoney()));
        textViewItems.setText(TextUtils.join(", ", trainer.getItems()));
    }

    private void updateTrainerName(String newName) {
        if (currentTrainer != null) {
            currentTrainer.setName(newName);
            updateTrainerInfo(currentTrainer);

            for (int i = 0; i < trainers.size(); i++) {
                if (trainers.get(i).getId() == currentTrainer.getId()) {
                    trainers.set(i, currentTrainer);
                    break;
                }
            }

            fileHandler.saveTrainers(trainers);
        }
    }


    private void showChangeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Trainer's Name");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            updateTrainerName(newName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Search Trainer");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Search", (dialog, which) -> {
            String searchName = input.getText().toString().trim();
            searchTrainer(searchName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to handle purchasing a Pokémon
    private void purchasePokemon(int cost) {
        if (currentTrainer != null) {
            if (currentTrainer.getMoney() >= cost) {
                currentTrainer.setMoney(currentTrainer.getMoney() - cost);
                updateTrainerInfo(currentTrainer);
                Toast.makeText(requireContext(), "You purchased a Pokémon", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Insufficient funds", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to handle receiving money
    private void receiveMoney(int amount) {
        if (currentTrainer != null) {
            currentTrainer.setMoney(currentTrainer.getMoney() + amount);
            updateTrainerInfo(currentTrainer);
            Toast.makeText(requireContext(), "You received money", Toast.LENGTH_SHORT).show();
        }
    }
}
