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
    package Miscs;

import com.google.gson.Gson;

import java.io.*;

import static Main.Main.TESTING;

    public class Levels {
        public int level, type, difficulty;

        public Levels() {
            new Miscs.Levels(1, 1);
        }

        public Levels(int level, int difficulty) {
            this.level = level;
            this.difficulty = difficulty;
            type = 1;
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
}