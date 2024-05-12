package edu.url.salle.magdalena.morag.pokeapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.url.salle.magdalena.morag.pokeapp.fragment.PokemonDetailFragment;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
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
                .replace(R.id.fragment_container, new PokemonDetailFragment())
                .commit();
    }

    public void getPokemonDetails(String nameOrId) {
        pokeApiService.getPokemonDetails(nameOrId).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(@NonNull Call<Pokemon> call, @NonNull Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();
                    if (pokemon != null) {
                        displayPokemonDetails(pokemon);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Fejl: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pokemon> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Fejl: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPokemonDetails(Pokemon pokemonDetailsResponse) {

        Pokemon pokemon = getPokemon(pokemonDetailsResponse);

        Fragment pokemonDetailFragment = PokemonDetailFragment.newInstance(pokemon);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, pokemonDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    private Pokemon getPokemon(Pokemon pokemonDetailsResponse) {
        int frontImageUrl = Integer.parseInt(getImageUrl(pokemonDetailsResponse.getId(), true));
        String pokeballImageUrl = getImageUrl(pokemonDetailsResponse.getId(), false);

        Pokemon pokemon = new Pokemon(
                pokemonDetailsResponse.getName(),
                frontImageUrl,
                pokeballImageUrl,
                false
        );
        return pokemon;
    }


    private String getImageUrl(int id, boolean isFrontImage) {
        String baseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        String imageFileName = id + (isFrontImage ? ".png" : "_back.png");
        return baseUrl + imageFileName;
    }

}
