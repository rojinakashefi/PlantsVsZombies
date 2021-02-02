package Miscs;

import java.util.ArrayList;

public class GameSave {
    public ArrayList<GameObjects> objects;
    public ArrayList<Integer> cards;
    public int gameTime;
    public GameSave(ArrayList<GameObjects> objects, int gameTime, ArrayList<Integer> cards) {
        this.objects = objects;
        this.gameTime = gameTime;
        this.cards = cards;
    }
}

