package Miscs;

import Menus.Game;
import com.google.gson.Gson;

import java.io.*;

import static Main.Main.TESTING;

public class Levels {
    public int level, difficulty;

    public Levels() {
        new Levels(1, 1);
    }

    public Levels(int level, int difficulty) {
        this.level = level;
        this.difficulty = difficulty;
    }
    public void gameSave(Game game) {
        Writer out;
        try {
            out = new FileWriter("lastSave.json", false);
            Gson jsonWriter = new Gson();
            jsonWriter.toJson(game, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("File Sent!");
    }
    public void save() {
        Writer out;
        try {
            out = new FileWriter("save.json", false);
            Gson jsonWriter = new Gson();
            jsonWriter.toJson(this, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("File Sent!");
    }
    public static Levels load() {
        Reader in;
        Levels temp = null;
        if (seek()) {
            try {
                in = new FileReader("save.json");
                Gson jsonReader = new Gson();
                temp = jsonReader.fromJson(in, Levels.class);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TESTING) System.out.println("File Loaded!");
            return temp;
        } else return null;
    }
    static boolean seek() { return new File("save.json").exists(); }

}
