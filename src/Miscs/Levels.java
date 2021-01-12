package Miscs;

import com.google.gson.Gson;

import java.io.*;

import static Main.Main.TESTING;

public class Levels {
    public int level, type, difficulty;

    public Levels() {
        new Levels(1, 1);
    }

    public Levels(int level, int difficulty) {
        this.level = level;
        this.difficulty = difficulty;
        type = 1;
    }
}