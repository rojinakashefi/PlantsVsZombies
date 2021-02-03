package Objects.Plants;

import Menus.Game;
import Miscs.Sounds;

import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.util.ArrayList;
/**
 * This class represents the plants conditions.
 * We sets the health , speed, position , the cost and set the Image of the plants .
 * @author Hashemipour
 * @since 2021
 */
public abstract class Plant extends JLabel {
    Container c;
    public int health;
    public int cost;
    public int speed;
    public int row, col;
    ImageIcon ready, die;
    public static ArrayList<Plant> plants = new ArrayList<>();

    /**
     * Main constructor of the Plant.class
     * @param c      the container.
     * @param health amount of health.
     * @param speed  the speed of the plant.
     * @param col    the column that the plant located.
     * @param row    the row that the plant located.
     * @param cost   the cost of the plant.
     */
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

    /**
     *This method represents the losing health of the plant that eaten by the zombies.
     * @param Amount the amount that will be reduced.
     */
    public void lossHealth(int Amount) {
        health -= Amount;
        Sounds.play(Sounds.EAT_PLANTS);
        if(health <= 0) {
            this.setIcon(die);
            Game.removePlant(this);
            Timer t = new Timer(1000, e -> {
                setIcon(null);
                c.remove(this);
            });
            c.remove(this);
            t.setRepeats(false);
            t.start();
        }
    }
}
