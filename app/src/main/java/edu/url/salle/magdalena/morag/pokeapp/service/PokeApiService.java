package edu.url.salle.magdalena.morag.pokeapp.service;

import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.fragment.PokemonFragment;
import edu.url.salle.magdalena.morag.pokeapp.model.Pokemon;
import edu.url.salle.magdalena.morag.pokeapp.model.PokemonListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PokeApiService {
    public interface PokeApi {
        @GET("pokemon")
        Call<PokemonListResponse> getAllPokemon();
    }
    private static Retrofit retrofit;

    public static PokeApi getPokeApi(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(PokeApi.class);
    }

}
