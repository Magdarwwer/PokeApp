package edu.url.salle.magdalena.morag.pokeapp.util;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import edu.url.salle.magdalena.morag.pokeapp.model.Trainer;

public class FileHandler {

    private static final String USER_FILE = "user.json.json";

    public static void saveUser(Context context, Trainer user) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            FileOutputStream fos = context.openFileOutput(USER_FILE, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("FileHandler", "Error saving user.json", e);
        }
    }

    public static Trainer loadUser(Context context) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/" + USER_FILE));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            br.close();
            Gson gson = new Gson();
            return gson.fromJson(json.toString(), Trainer.class);
        } catch (IOException e) {
            Log.e("FileHandler", "Error loading user.json", e);
            return null;
        }
    }
}
