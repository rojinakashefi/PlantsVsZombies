package Objects.Plants;

import Menus.Game;
import Miscs.Sounds;

import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.util.ArrayList;

public abstract class Plant extends JLabel {
    Container c;
    public int health;
    public int cost;
    public int speed;
    public int row, col;
    ImageIcon ready, die;
    public static ArrayList<Plant> plants = new ArrayList<>();

    public Plant(Container c, int health, int speed,int col, int row, int cost) {
        this.c = c;
        this.health = health;
        this.speed = speed;
        this.col = col;
        this.row = row;
        this.cost = cost;
        Plant.plants.add(this);
        c.add(this);
    }

    public void lossHealth() {
        health -= 30;
        Sounds.play(Sounds.EAT_PLANTS);
        if(health <= 0) {
            this.setIcon(die);
            Game.removePlant(this);
            Timer t = new Timer(1000, e -> c.remove(this));
            c.remove(this);
            t.setRepeats(false);
            t.start();
        }
    }
}
