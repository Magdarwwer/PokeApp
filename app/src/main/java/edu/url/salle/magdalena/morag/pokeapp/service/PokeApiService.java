package edu.url.salle.magdalena.morag.pokeapp.service;

import edu.url.salle.magdalena.morag.pokeapp.model.PokemonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("Pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}