package edu.url.salle.magdalena.morag.pokeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class TrainerFragment extends Fragment {

    private TextView textViewTrainerName;
    private TextView textViewTrainerMoney;
    private TextView textViewItems ;
    private TextView textViewCapturedPokemons ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trainer, container, false);
        textViewTrainerName = root.findViewById(R.id.textViewTrainerName);
        textViewTrainerMoney = root.findViewById(R.id.textViewTrainerMoney);
        textViewItems  = root.findViewById(R.id.textViewItems);
        textViewCapturedPokemons  = root.findViewById(R.id.textViewCapturedPokemons );

        // Hardcoded trainer with information
        Trainer ash = new Trainer("Ash", 1000, new ArrayList<>(), new ArrayList<>());

        textViewTrainerName.setText(ash.getName());
        textViewTrainerMoney.setText(getString(R.string.money_format, ash.getMoney()));

        return root;
    }
}
