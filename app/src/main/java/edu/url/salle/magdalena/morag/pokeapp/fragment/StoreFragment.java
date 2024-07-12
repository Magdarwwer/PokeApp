package edu.url.salle.magdalena.morag.pokeapp.fragment;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.adapter.ItemAdapter;
import edu.url.salle.magdalena.morag.pokeapp.model.Store;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;
import edu.url.salle.magdalena.morag.pokeapp.model.TrainerManager;

public class StoreFragment extends Fragment implements View.OnClickListener {

    private int money;
    private Trainer trainer;
    private TrainerManager trainerManager;
    private ItemAdapter itemAdapter;

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        trainerManager = TrainerManager.getInstance();
        itemAdapter = new ItemAdapter(getContext());

        Button buttonBuyPokeball = root.findViewById(R.id.buttonBuyPokeball);
        Button buttonBuySuperball = root.findViewById(R.id.buttonBuySuperball);
        Button buttonBuyUltraball = root.findViewById(R.id.buttonBuyUltraball);
        Button buttonBuyMasterball = root.findViewById(R.id.buttonBuyMasterball);

        buttonBuyPokeball.setOnClickListener(this);
        buttonBuySuperball.setOnClickListener(this);
        buttonBuyUltraball.setOnClickListener(this);
        buttonBuyMasterball.setOnClickListener(this);

        if (trainer != null) {
            money = trainer.getMoney();
        } else {
            money = 0;
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        int price = 0;
        String itemName = "";

        if (v.getId() == R.id.buttonBuyPokeball) {
            price = Store.POKEBALL_PRICE;
            itemName = "Pokeball";
        } else if (v.getId() == R.id.buttonBuySuperball) {
            price = Store.SUPERBALL_PRICE;
            itemName = "Superball";
        } else if (v.getId() == R.id.buttonBuyUltraball) {
            price = Store.ULTRABALL_PRICE;
            itemName = "Ultraball";
        } else if (v.getId() == R.id.buttonBuyMasterball) {
            price = Store.MASTERBALL_PRICE;
            itemName = "Masterball";
        }

        showBuyConfirmationDialog(itemName, price);
    }

    private void showBuyConfirmationDialog(String itemName, int price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Buy " + itemName + "?");
        builder.setMessage("Are you sure you want to buy " + itemName + " for " + price + " coins?");

        builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("TrainerData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                buyItem(itemName, price, editor);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void buyItem(String itemName, int price, SharedPreferences.Editor editor) {
        if (trainer != null) {
            if (trainer.getMoney() >= price) {
                int remainingMoney = trainer.getMoney() - price;
                trainer.setMoney(remainingMoney);
                trainer.addItem(itemName);

                Toast.makeText(requireContext(), "You have bought a " + itemName + " for " + price + " coins.", Toast.LENGTH_SHORT).show();

                trainerManager.saveTrainerData(trainer, editor);
                itemAdapter.setItems(new ArrayList<>(trainer.getItems()));
            } else {
                Toast.makeText(requireContext(), "Not enough money to buy " + itemName + ".", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
