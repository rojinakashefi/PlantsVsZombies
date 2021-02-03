package Miscs;

import Menus.RankingMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Main.Main.*;

/**
 * This Class is all about saving, loading and keeping player level, data, etc.
 */
public class Player implements Comparable<Player> {
    public int difficulty;
    public int wins, losses, score;
    public String username;

    public Player() {
        new Player(0, 0, 0, 0, "newbie");
    }

    /**
     * Main constructor of the Player.class
     * @param difficulty the difficulty of the game
     * @param wins the count of wins
     * @param losses the count of losses
     * @param score the score of the current player
     * @param username the unique name of the player
     */
    public Player(int difficulty, int wins, int losses, int score, String username) {
        this.wins = wins;
        this.losses = losses;
        this.score = score;
        this.username = username;
        this.difficulty = difficulty;
    }

    /**
     * Saves the given player in the local space
     * @param players the players to be saved
     */
    public static void save(ArrayList<Player> players) {
        Writer out;
        try {
            out = new FileWriter("save.json", false);
            Gson jsonWriter = new Gson();
            jsonWriter.toJson(players, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("File Sent!");
    }

    /**
     * @return the list of all players on the local space
     */
    public static ArrayList<Player> load() {
        Reader in;
        ArrayList<Player> temp = new ArrayList<>();
        if (seek("save")) {
            try {
                in = new FileReader("save.json");
                Gson jsonReader = new Gson();
                temp = jsonReader.fromJson(in, new TypeToken<List<Player>>(){}.getType());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TESTING) System.out.println("File Loaded!");
            return temp;
        } else return null;
    }
    static boolean seek(String name) { return new File(name + ".json").exists(); }

    /**
     * This method is not used directly on this project
     * the usage is in the Arrays.sort in Ranking class
     * @see RankingMenu#RankingMenu() For the usage of Array.sort();
     * @see Arrays#toString(Object[])#compareTo(Player) Method in the Library
     * @param compare the player to compare
     * @return compared number of the player
     */
    @Override
    public int compareTo(Player compare) {
        int compareScore=(compare).score;
        return this.score - compareScore;
    }

    /**
     * saves the player to the list
     */
    public void save() {
        if (!loadedPlayers.isEmpty()) {
            loadedPlayers.remove(loadedPlayers.get(findPlayerIndex(this.username)));
            loadedPlayers.add(this);
        }
    }
}

