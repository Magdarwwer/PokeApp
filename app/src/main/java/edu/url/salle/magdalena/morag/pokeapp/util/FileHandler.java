package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class FileHandler {

    private static final String TRAINERS_FILE = "trainers.json";
    private Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    public void saveTrainers(List<Trainer> trainers) {
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

    public List<Trainer> loadTrainers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/" + TRAINERS_FILE));
            Gson gson = new Gson();
            Trainer[] trainersArray = gson.fromJson(br, Trainer[].class);
            br.close();
            return Arrays.asList(trainersArray);
        } catch (IOException e) {
            Log.e("FileHandler", "Error loading trainers", e);
            return null;
        }
    }

}
