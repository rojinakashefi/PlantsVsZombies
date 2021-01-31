package Miscs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Main.Main.*;

public class Levels implements Comparable<Levels> {
    public int difficulty;
    public int wins, losses, score;
    public String username;

    public Levels() {
        new Levels(1, 0, 0, 0, "newbie");
    }

    public Levels(int difficulty, int wins, int losses, int score, String username) {
        this.wins = wins;
        this.losses = losses;
        this.score = score;
        this.username = username;
        this.difficulty = difficulty;
    }
    public static void save(ArrayList<Levels> levels) {
        Writer out;
        try {
            out = new FileWriter("save.json", false);
            Gson jsonWriter = new Gson();
            jsonWriter.toJson(levels, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("File Sent!");
    }
    public static ArrayList<Levels> load() {
        Reader in;
        ArrayList<Levels> temp = new ArrayList<>();
        if (seek()) {
            try {
                in = new FileReader("save.json");
                Gson jsonReader = new Gson();
                temp = jsonReader.fromJson(in, new TypeToken<List<Levels>>(){}.getType());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TESTING) System.out.println("File Loaded!");
            System.out.println(Arrays.toString(temp.toArray()));
            return temp;
        } else return null;
    }
    static boolean seek() { return new File("save.json").exists(); }

    @Override
    public int compareTo(Levels compare) {
        int compareScore=(compare).score;
        return this.score - compareScore;
    }
    public void save() {
        loadedPlayers.remove(loadedPlayers.get(findPlayerIndex(this.username)));
        loadedPlayers.add(this);
    }
}