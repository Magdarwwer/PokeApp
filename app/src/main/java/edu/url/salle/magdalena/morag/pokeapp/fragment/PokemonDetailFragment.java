package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;

public class PokemonDetailFragment extends Fragment {
    private TextView textViewName;
    private ImageView imageViewFront;
    private ImageView imageViewBack;
    private TextView textViewTypes;
    private TextView textViewDescription;
    private TextView textViewAbility;
    private TextView textViewStats;
    private Button buttonCapture;

    private Pokemon pokemon;

    public PokemonDetailFragment() {
        // Required empty public constructor
    }

    public static PokemonDetailFragment newInstance(Pokemon pokemon) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("pokemon", (Serializable) pokemon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable("pokemon");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        textViewName = view.findViewById(R.id.textViewName);
        imageViewFront = view.findViewById(R.id.imageViewFront);
        imageViewBack = view.findViewById(R.id.imageViewBack);
        textViewTypes = view.findViewById(R.id.textViewTypes);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        textViewAbility = view.findViewById(R.id.textViewAbility);
        textViewStats = view.findViewById(R.id.textViewStats);
        buttonCapture = view.findViewById(R.id.buttonCapture);

        if (pokemon != null) {
            textViewName.setText(pokemon.getName());
            imageViewFront.setImageResource(pokemon.getFrontImage());
            imageViewBack.setImageResource(pokemon.getBackImage());
            textViewTypes.setText(formatTypes(pokemon.getTypes()));
            textViewDescription.setText(pokemon.getDescription());
            textViewAbility.setText(chooseAbility(pokemon.getAbilities()));
            textViewStats.setText(formatStats(pokemon.getStats()));
        }

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pokemon != null && !pokemon.isCaptured()) {
                    pokemon.capturePokemon();
                    buttonCapture.setEnabled(false);
                    buttonCapture.setText("Captured");
                }
            }
        });

        return view;
    }

    private String formatTypes(List<String> types) {
        StringBuilder formattedTypes = new StringBuilder();
        for (String type : types) {
            formattedTypes.append(type).append(", ");
        }
        return formattedTypes.substring(0, formattedTypes.length() - 2);
    }

    private String chooseAbility(List<String> abilities) {
        Random random = new Random();
        int randomValue = random.nextInt(100);
        if (abilities.size() == 1) {
            return abilities.get(0);
        } else {
            if (randomValue < 25) {
                return abilities.get(abilities.size() - 1); // Hidden ability
            } else if (randomValue < 75) {
                return abilities.get(random.nextInt(abilities.size() - 1));
            } else {
                return abilities.get(random.nextInt(abilities.size() - 1));
            }
        }
    }

    private String formatStats(List<String> stats) {
        StringBuilder formattedStats = new StringBuilder();
        for (String stat : stats) {
            formattedStats.append(stat).append("\n");
        }
        return formattedStats.toString();
    }
}
