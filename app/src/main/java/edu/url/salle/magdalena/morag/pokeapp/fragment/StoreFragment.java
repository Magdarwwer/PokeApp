package edu.url.salle.magdalena.morag.pokeapp.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Store;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoreFragment extends Fragment implements View.OnClickListener {

    private int money; // The current amount of money the trainer has

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        TextView textViewPokeballPrice = root.findViewById(R.id.textViewPokeballPrice);
        TextView textViewSuperballPrice = root.findViewById(R.id.textViewSuperballPrice);
        TextView textViewUltraballPrice = root.findViewById(R.id.textViewUltraballPrice);
        TextView textViewMasterballPrice = root.findViewById(R.id.textViewMasterballPrice);

        Button buttonBuyPokeball = root.findViewById(R.id.buttonBuyPokeball);
        Button buttonBuySuperball = root.findViewById(R.id.buttonBuySuperball);
        Button buttonBuyUltraball = root.findViewById(R.id.buttonBuyUltraball);
        Button buttonBuyMasterball = root.findViewById(R.id.buttonBuyMasterball);

        buttonBuyPokeball.setOnClickListener(this);
        buttonBuySuperball.setOnClickListener(this);
        buttonBuyUltraball.setOnClickListener(this);
        buttonBuyMasterball.setOnClickListener(this);

        textViewPokeballPrice.setText(getString(R.string.price_format, Store.POKEBALL_PRICE));
        textViewSuperballPrice.setText(getString(R.string.price_format, Store.SUPERBALL_PRICE));
        textViewUltraballPrice.setText(getString(R.string.price_format, Store.ULTRABALL_PRICE));
        textViewMasterballPrice.setText(getString(R.string.price_format, Store.MASTERBALL_PRICE));

        money = 1000;

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

        buyItem(itemName, price);
    }

    private void buyItem(String itemName, int price) {
        if (money >= price) {
            money -= price;
            Toast.makeText(requireContext(), "Bought " + itemName + " for " + price + " coins.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Not enough money to buy " + itemName + ".", Toast.LENGTH_SHORT).show();
        }
    }
}
