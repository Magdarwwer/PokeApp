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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.adapter.CapturedPokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.adapter.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.util.PokemonSharedPreferences;

public class TrainerFragment extends Fragment {

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private TextView textViewItems;
    private ArrayList<Trainer> trainers;
    private Trainer currentTrainer;
    private View root;
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private CapturedPokemonAdapter adapter;
    private PokemonAdapter pokemonAdapter;

    public TrainerFragment(Trainer currentTrainer) {
        this.currentTrainer = currentTrainer;
        trainers = new ArrayList<>();
    }
    public TrainerFragment() {
        trainers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = root.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = root.findViewById(R.id.textViewTrainerMoney);
        textViewItems = root.findViewById(R.id.textViewItems);
        recyclerView = root.findViewById(R.id.recyclerViewCapturedPokemons);

        pokemonAdapter = new PokemonAdapter(getContext());

        Trainer trainer1 = new Trainer(1, "Ash", 1000, new ArrayList<>(), new ArrayList<>());
        trainer1.getPokedex().add(new Pokemon(25,"Pikachu", true,"Pokeball" ));
        trainer1.getItems().add("Pokeball");
        trainers.add(trainer1);

        Trainer trainer2 = new Trainer(2, "Misty", 800, new ArrayList<>(), new ArrayList<>());
        trainer2.getPokedex().add(new Pokemon(54, "Psyduck", true, "Masterball"));
        trainer2.getItems().add("Masterball");
        trainers.add(trainer2);

        Trainer trainer3 = new Trainer(3, "Brock", 1200, new ArrayList<>(), new ArrayList<>());
        trainer3.getPokedex().add(new Pokemon(74,"Geodude", true, "Superball"));
        trainer3.getItems().add("Superball");
        trainers.add(trainer3);

        sharedPreferences = requireContext().getSharedPreferences("TrainerPrefs", Context.MODE_PRIVATE);


        if (currentTrainer != null) {
            ArrayList<Pokemon> caughtPokemon = PokemonSharedPreferences.loadCaughtPokemon(requireContext(), currentTrainer.getId());
            if (caughtPokemon != null) {
                currentTrainer.setPokedex(caughtPokemon);
            }
        }

        Button openDialogButton = root.findViewById(R.id.buttonOpenDialog);
        openDialogButton.setOnClickListener(v -> showChangeNameDialog());

        Button searchButton = root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(v -> showSearchDialog());

        Button releaseButton = root.findViewById(R.id.buttonReleasePokemon);
        releaseButton.setOnClickListener(v -> {
            if (currentTrainer != null) {
                showReleasePokemonDialog();
            }
        });

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        if (currentTrainer != null && currentTrainer.getPokedex() != null) {
            adapter = new CapturedPokemonAdapter(currentTrainer.getPokedex(), getContext(), pokemonAdapter);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(requireContext(), "No captured pokemons available", Toast.LENGTH_SHORT).show();
        }


        return root;
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

    private void showReleasePokemonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Pokemon to Release");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Loop through the Pokemon list to create TextViews dynamically
        for (Pokemon pokemon : currentTrainer.getPokedex()) {
            TextView textView = new TextView(requireContext());
            textView.setText(pokemon.getName());
            textView.setPadding(16, 16, 16, 16);
            textView.setTextSize(18);

            layout.addView(textView);
        }

        builder.setView(layout);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        final AlertDialog dialog = builder.create();

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setOnClickListener(v -> {
                TextView textView = (TextView) v;
                String pokemonName = textView.getText().toString();

                Pokemon selectedPokemon = null;
                for (Pokemon pokemon : currentTrainer.getPokedex()) {
                    if (pokemon.getName().equals(pokemonName)) {
                        selectedPokemon = pokemon;
                        break;
                    }
                }
                if (selectedPokemon != null) {
                    releasePokemon(selectedPokemon);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void releasePokemon(Pokemon pokemon) {
        if (currentTrainer != null && currentTrainer.getPokedex() != null) {
            currentTrainer.getPokedex().remove(pokemon);
            updateTrainerInfo(currentTrainer);
            Toast.makeText(requireContext(), "You released " + pokemon.getName(), Toast.LENGTH_SHORT).show();
        }
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
        }
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

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("searched_trainer_id", currentTrainer.getId());
                editor.apply();

                return;
            }
        }
        Toast.makeText(requireContext(), "Trainer not found", Toast.LENGTH_SHORT).show();
    }

    public void updateTrainerInfo(Trainer trainer) {
        textViewTrainerName.setText(trainer.getName());
        textViewTrainerMoney.setText(getString(R.string.money_format, trainer.getMoney()));
        textViewItems.setText(TextUtils.join(", ", trainer.getItems()));
        PokemonSharedPreferences.saveCaughtPokemon(requireContext(), trainer.getId(), trainer.getPokedex());

        if (adapter != null) {
            adapter.updateCapturedPokemons(trainer.getPokedex());
        }
    }






}
