package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Plant extends JLabel {
    public int health;
    public int speed;
    ImageIcon ready;
    public static ArrayList<Plant> plants = new ArrayList<>();

    public Plant(Container c, int health, int speed) {
        this.health = health;
        this.speed = speed;
        Plant.plants.add(this);
        c.add(this);
    }
    //public abstract void lossHealth(int Amount);
}
