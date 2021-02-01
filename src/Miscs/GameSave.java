package Miscs;

import java.awt.*;
import java.util.ArrayList;

public class GameSave {
    public ArrayList<gameObjects> objects;
    public ArrayList<Integer> cards;
    public int gameTime;
    public GameSave(ArrayList<gameObjects> objects, int gameTime, ArrayList<Integer> cards) {
        this.objects = objects;
        this.gameTime = gameTime;
        this.cards = cards;
    }
}

