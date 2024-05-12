package edu.url.salle.magdalena.morag.pokeapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonListResponse;
import edu.url.salle.magdalena.morag.pokeapp.service.PokeApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromAPI();
    }

    private void fetchDataFromAPI() {
        PokeApiService.getPokedex(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse pokemonListResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonListResponse.getResults();
                    adapter = new PokemonAdapter(pokemonList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
