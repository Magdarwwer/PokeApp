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

        // Define items for trainers
        List<String> ashItems = Arrays.asList("Potion", "Revive", "Great Ball");
        List<String> mistyItems = Arrays.asList("Potion", "Super Potion", "Ultra Ball");
        List<String> brockItems = Arrays.asList("Potion", "Max Potion", "Master Ball");

        // Create trainers
        trainers = new ArrayList<>();
        Trainer ash = new Trainer(1, "Ash", 1000, new ArrayList<>(), new ArrayList<>());
        Trainer misty = new Trainer(2, "Misty", 1500, new ArrayList<>(), new ArrayList<>());
        Trainer brock = new Trainer(3, "Brock", 1200, new ArrayList<>(), new ArrayList<>());

        // Add trainers to the list
        trainers.add(ash);
        trainers.add(misty);
        trainers.add(brock);

        // Define items list for each trainer
        List<List<String>> items = new ArrayList<>();
        items.add(ashItems);
        items.add(mistyItems);
        items.add(brockItems);

        // Assign items to trainers
        for (int i = 0; i < trainers.size(); i++) {
            Trainer trainer = trainers.get(i);
            List<String> trainerItems = items.get(i);
            trainer.getItems().addAll(trainerItems);
        }

        // Set up buttons
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

    private void handleMoneyCalculation(int moneySpent) {
        if (currentTrainer != null) {
            int currentMoney = currentTrainer.getMoney();
            int remainingMoney = currentMoney - moneySpent;

            if (remainingMoney >= 0) {
                currentTrainer.setMoney(remainingMoney);
                updateTrainerInfo(currentTrainer);
                Toast.makeText(requireContext(), "Transaction successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Insufficient funds", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
