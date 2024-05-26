package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class FileHandler {

    private static final String FILE_NAME = "trainers.json";

    public static void saveTrainers(Context context, List<Trainer> trainers) {
        Gson gson = new Gson();
        String json = gson.toJson(trainers);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            Log.d("FileHandler", "Trainers saved successfully.");
        } catch (IOException e) {
            Log.e("FileHandler", "Error saving trainers: " + e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e("FileHandler", "Error closing file output stream: " + e.getMessage());
                }
            }
        }
    }

    public static ArrayList<Trainer> loadTrainers(Context context) {
        ArrayList<Trainer> trainers = new ArrayList<>();

        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String json = stringBuilder.toString();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Trainer>>() {}.getType();
            trainers = gson.fromJson(json, listType);
            Log.d("FileHandler", "Trainers loaded successfully.");
        } catch (IOException e) {
            Log.e("FileHandler", "Error loading trainers: " + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e("FileHandler", "Error closing buffered reader: " + e.getMessage());
                }
            }
        }

        return trainers;
    }
}
