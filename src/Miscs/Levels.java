package Miscs;

import Menus.Game;


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

    }
    public void save() {
        Writer out;

    }
    public static Levels load() {
        Reader in;
        Levels temp = null;
       return null;
    }
    static boolean seek() { return new File("save.json").exists(); }
}
