package edu.url.salle.magdalena.morag.pokeapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokeApiService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    // Method to get the list of Pokémon
    public JSONArray getPokemonList() throws IOException, JSONException {
        URL url = new URL(BASE_URL + "pokemon");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONArray("results");
    }

    // Method to get details of a specific Pokémon by name or ID
    public JSONObject getPokemonDetails(String nameOrId) throws IOException, JSONException {
        URL url = new URL(BASE_URL + "pokemon/" + nameOrId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}