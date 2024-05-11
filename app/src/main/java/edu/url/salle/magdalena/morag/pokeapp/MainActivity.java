package edu.url.salle.magdalena.morag.pokeapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import edu.url.salle.magdalena.morag.pokeapp.fragment.PokedexFragment;
import edu.url.salle.magdalena.morag.pokeapp.fragment.StoreFragment;
import edu.url.salle.magdalena.morag.pokeapp.fragment.TrainerFragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PokedexFragment())
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.navigation_pokedex) {
                        selectedFragment = new PokedexFragment();
                    } else if (itemId == R.id.navigation_trainer) {
                        selectedFragment = new TrainerFragment();
                    } else if (itemId == R.id.navigation_store) {
                        selectedFragment = new StoreFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    } else {
                        return false;
                    }
                }
            };
}