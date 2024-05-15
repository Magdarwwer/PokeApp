package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.ItemAdapter;
import edu.url.salle.magdalena.morag.pokeapp.PokemonAdapter;
import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Item;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class TrainerFragment extends Fragment {

    private EditText editTextTrainerName;
    private TextView textViewMoney;
    private RecyclerView recyclerViewItems;
    private RecyclerView recyclerViewCapturedPokemons;
    private Trainer trainer;

    // Dummy data to test
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Item> items = new ArrayList<>();
        items.add(new Item("Potion", 5));
        items.add(new Item("Revive", 2));

        // Initialize captured pokemons list
        List<Pokemon> capturedPokemons = new ArrayList<>();
        capturedPokemons.add(new Pokemon("Pikachu", "pikachu_front_image_url", "pokeball_image_url"));

        trainer = new Trainer("Ash", 1000, items, capturedPokemons); // Corrected order
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainer, container, false);
        editTextTrainerName = root.findViewById(R.id.editTextTrainerName);
        textViewMoney = root.findViewById(R.id.textViewMoney);
        recyclerViewItems = root.findViewById(R.id.recyclerViewItems);
        recyclerViewCapturedPokemons = root.findViewById(R.id.recyclerViewCapturedPokemons);

        // Initialize RecyclerViews
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewCapturedPokemons.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Display trainer's name
        editTextTrainerName.setText(trainer.getName());

        // Display trainer's money
        textViewMoney.setText(getString(R.string.money_format, trainer.getMoney()));

        // Display trainer's items
        ItemAdapter itemAdapter = new ItemAdapter(trainer.getItems());
        recyclerViewItems.setAdapter(itemAdapter);

        // Display trainer's captured pokemons
        PokemonAdapter pokemonAdapter = new PokemonAdapter();
        recyclerViewCapturedPokemons.setAdapter(pokemonAdapter);

        // Set up listener for trainer name update
        editTextTrainerName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String newName = editTextTrainerName.getText().toString().trim();
                trainer.updateName(newName);
                // Update UI to reflect the new name
                return true;
            }
            return false;
        });

        return root;
    }
}

