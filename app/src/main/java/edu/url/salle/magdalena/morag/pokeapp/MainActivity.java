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
import android.widget.Toast;

import edu.url.salle.magdalena.morag.pokeapp.model.PokemonDetailsResponse;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonListResponse;
import edu.url.salle.magdalena.morag.pokeapp.service.PokeApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PokeApiService pokeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokeApiService = new PokeApiService();

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
    public void getAllPokemon() {
        pokeApiService.getAllPokemon().enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();
                } else {
                    Toast.makeText(MainActivity.this, "Fejl: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fejl: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPokemonDetails(String nameOrId) {
        pokeApiService.getPokemonDetails(nameOrId).enqueue(new Callback<PokemonDetailsResponse>() {
            @Override
            public void onResponse(Call<PokemonDetailsResponse> call, Response<PokemonDetailsResponse> response) {
                if (response.isSuccessful()) {
                    PokemonDetailsResponse pokemonDetailsResponse = response.body();
                } else {
                    Toast.makeText(MainActivity.this, "Fejl: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDetailsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fejl: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}