package edu.url.salle.magdalena.morag.pokeapp.fragment;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Store;
import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

import android.widget.Toast;

public class StoreFragment extends Fragment implements View.OnClickListener {

    private int money;
    private Trainer trainer;
    private TrainerFragment trainerFragment;

    public void setTrainerFragment(TrainerFragment fragment) {
        this.trainerFragment = fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

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
               // buyItem(itemName, price);
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

    /*private void buyItem(String itemName, int price) {
        if (trainer != null) {
            if (trainer.getMoney() >= price) {
                int remainingMoney = trainer.getMoney() - price;
                trainer.setMoney(remainingMoney);
                Toast.makeText(requireContext(), "Bought " + itemName + " for " + price + " coins.", Toast.LENGTH_SHORT).show();
                trainerFragment.updateTrainer(trainer);
            } else {
                Toast.makeText(requireContext(), "Not enough money to buy " + itemName + ".", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
