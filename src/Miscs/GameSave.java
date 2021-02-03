package Miscs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static Main.Main.TESTING;
import static Miscs.Player.seek;

/**
 * GameSave class performed saving and loading of this project.
 * This class saving each elements  of  the game for using the loading option.
 * @author Hashemipour
 * @since 2021
 */
public class GameSave {
    public ArrayList<GameObjects> objects;
    public ArrayList<Integer> cards;
    public long gameTime;
    public int suns;

    /**
     * Main constructor of the GameSave.class
     * @param objects   List of objects.
     * @param gameTime  The game time.
     * @param cards     List of cards.
     * @param suns      number of suns of the game .
     */
    public GameSave(ArrayList<GameObjects> objects, long gameTime, ArrayList<Integer> cards, int suns) {
        this.objects = objects;
        this.gameTime = gameTime;
        this.cards = cards;
        this.suns = suns;
    }

    /**
     * This method reproduced the save option with writing situation on the file.
     * @param saves List of game that we saved.
     * @param name  name of the player.
     */
    public static void save(ArrayList<GameSave> saves, String name) {
        Writer out;
        try {
            out = new FileWriter(name + ".json", false);
            Gson jsonWriter = new Gson();
            jsonWriter.toJson(saves, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("File Sent!");
    }

    /**
     * load method reproduced the load option with reading from the file.
     * @param  name name of the player.
     * @return list of temp(the game that we read from file) or null
     */
    public static ArrayList<GameSave> load(String name) {
        Reader in;
        ArrayList<GameSave> temp = new ArrayList<>();
        if (seek(name)) {
            try {
                in = new FileReader(name + ".json");
                Gson jsonReader = new Gson();
                temp = jsonReader.fromJson(in, new TypeToken<List<GameSave>>(){}.getType());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TESTING) System.out.println("File Loaded!");
            return temp;
        } else return null;
    }
}

