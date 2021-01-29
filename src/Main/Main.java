package Main;

import Miscs.Levels;


import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<Levels> loadedPlayers = new ArrayList<>();
    public static final boolean TESTING = true;

    public static void main(String[] args) {
    }
    public static int findPlayerIndex(String name) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (loadedPlayers.get(i).username.equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
