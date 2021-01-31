package Objects.Zombies;

import Menus.Game;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.Container;
import java.util.ArrayList;

public abstract class Zombie extends JLabel {
    Container c;
    boolean frozen = false;
    public int row;
    public int health;
    public int speed;
    public int damage;
    ImageIcon walk, die, burned = new ImageIcon("gfx/burn.pvz");
    public int sizeX = 81;
    public int sizeY = 130;
    public static ArrayList<Zombie> zombies = new ArrayList<>();
    String hitSound = Sounds.NONE;

    public Zombie(Container c, int health, int speed, int damage, int row) {
        this.c = c;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        Zombie.zombies.add(this);
        c.add(this);
        this.row = row;
    }

    public abstract void lossHealth(int Amount, boolean isFrozen);
    public void kill(boolean burn) {
        health = 0;
        Sounds.play(hitSound);
        this.setIcon(burn?burned:die);
        if (burn && getClass() == PoleVaulting.class || getClass() == Newspaper.class)
            setBounds(getX() + 200, getY(),  getIcon().getIconWidth(), getIcon().getIconHeight());
        Game.removeZombie(this);
        Timer t = new Timer(getClass()==Football.class?500:1000, e -> {
            this.setIcon(null);
            c.remove(this);
        });
        t.setRepeats(false);
        t.start();
    }
}
