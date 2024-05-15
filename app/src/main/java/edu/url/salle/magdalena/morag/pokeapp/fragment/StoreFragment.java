package edu.url.salle.magdalena.morag.pokeapp.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.url.salle.magdalena.morag.pokeapp.R;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoreFragment extends Fragment implements View.OnClickListener {

    private TextView textViewPokeballPrice, textViewSuperballPrice, textViewUltraballPrice, textViewMasterballPrice;
    private Button buttonBuyPokeball, buttonBuySuperball, buttonBuyUltraball, buttonBuyMasterball;

    private int money; // The current amount of money the trainer has

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        // Initialize views
        textViewPokeballPrice = root.findViewById(R.id.textViewPokeballPrice);
        textViewSuperballPrice = root.findViewById(R.id.textViewSuperballPrice);
        textViewUltraballPrice = root.findViewById(R.id.textViewUltraballPrice);
        textViewMasterballPrice = root.findViewById(R.id.textViewMasterballPrice);

        buttonBuyPokeball = root.findViewById(R.id.buttonBuyPokeball);
        buttonBuySuperball = root.findViewById(R.id.buttonBuySuperball);
        buttonBuyUltraball = root.findViewById(R.id.buttonBuyUltraball);
        buttonBuyMasterball = root.findViewById(R.id.buttonBuyMasterball);

        // Set onClickListener for buttons
        buttonBuyPokeball.setOnClickListener(this);
        buttonBuySuperball.setOnClickListener(this);
        buttonBuyUltraball.setOnClickListener(this);
        buttonBuyMasterball.setOnClickListener(this);

        // Set item prices
        textViewPokeballPrice.setText(getString(R.string.price_format, 200));
        textViewSuperballPrice.setText(getString(R.string.price_format, 500));
        textViewUltraballPrice.setText(getString(R.string.price_format, 1500));
        textViewMasterballPrice.setText(getString(R.string.price_format, 100000));

        // Set initial money
        money = 1000; // Initial amount of money

        return root;
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBuyPokeball:
                buyItem("Pokeball", 200);
                break;
            case R.id.buttonBuySuperball:
                buyItem("Superball", 500);
                break;
            case R.id.buttonBuyUltraball:
                buyItem("Ultraball", 1500);
                break;
            case R.id.buttonBuyMasterball:
                buyItem("Masterball", 100000);
                break;
        }
    }*/

    private void buyItem(String itemName, int price) {
        if (money >= price) {
            // Subtract the price from the trainer's money
            money -= price;
            // Update the UI to reflect the new amount of money
            Toast.makeText(requireContext(), "Bought " + itemName + " for " + price + " coins.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Not enough money to buy " + itemName + ".", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
