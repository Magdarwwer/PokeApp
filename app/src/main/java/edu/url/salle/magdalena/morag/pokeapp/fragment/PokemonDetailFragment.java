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

        }

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement capture logic
            }
        });

        return view;
    }

}
