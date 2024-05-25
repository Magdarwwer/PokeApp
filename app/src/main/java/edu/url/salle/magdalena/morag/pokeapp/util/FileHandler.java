package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class FileHandler {

    private static final String TRAINERS_FILE = "trainers.json";
    private Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    public void saveTrainers(ArrayList<Trainer> trainers) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(trainers);
            FileOutputStream fos = context.openFileOutput(TRAINERS_FILE, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("FileHandler", "Error saving trainers", e);
        }
    }

    public ArrayList<Trainer> loadTrainers() {
        ArrayList<Trainer> loadedTrainers = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/" + TRAINERS_FILE));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
            br.close();

            String json = jsonBuilder.toString();
            Gson gson = new Gson();
            Trainer[] trainersArray = gson.fromJson(json, Trainer[].class);
            if (trainersArray != null) {
                loadedTrainers.addAll(Arrays.asList(trainersArray));
            }
        } catch (IOException e) {
            Log.e("FileHandler", "Error loading trainers", e);
        }

        return loadedTrainers;
    }
}
