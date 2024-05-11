package edu.url.salle.magdalena.morag.pokeapp.service;

import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonDetailsResponse;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PokeApiService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public interface PokeApi {
        @GET("pokemon")
        Call<PokemonListResponse> getAllPokemon();

        @GET("pokemon/{nameOrId}")
        Call<PokemonDetailsResponse> getPokemonDetails(@Path("nameOrId") String nameOrId);

        @GET("pokemon")
        Call<List<Pokemon>> getPokedex(@Path("nameOrId") String nameOrId);
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final PokeApi pokeApi = retrofit.create(PokeApi.class);

    public static Call<PokemonListResponse> getAllPokemon() {
        return pokeApi.getAllPokemon();
    }

    public static Call<PokemonDetailsResponse> getPokemonDetails(String nameOrId) {
        return pokeApi.getPokemonDetails(nameOrId);
    }

    public static Call<List<Pokemon>> getPokedex(String nameOrId){
        return pokeApi.getPokedex(nameOrId);
    }
}
