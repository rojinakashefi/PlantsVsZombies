package Miscs;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static Main.Main.TESTING;
import static Miscs.Player.seek;

public class GameSave {
    public ArrayList<GameObjects> objects;
    public ArrayList<Integer> cards;
    public long gameTime;
    public int suns;
    public GameSave(ArrayList<GameObjects> objects, long gameTime, ArrayList<Integer> cards, int suns) {
        this.objects = objects;
        this.gameTime = gameTime;
        this.cards = cards;
        this.suns = suns;
    }

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
    public static ArrayList<GameSave> load(String name) {
//        Reader in;
//        ArrayList<GameSave> temp = new ArrayList<>();
//
//        if (seek(name)) {
//            try {
//                in = new FileReader(name + ".json");
//                Gson jsonReader = new Gson();
//                temp = jsonReader.fromJson(in, new TypeToken<List<GameSave>>(){}.getType());
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (TESTING) System.out.println("File Loaded!");
//            return temp;
//        } else return null;
//    }
}

