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
        if (burn && getClass() == PoleVaulting.class)
            setBounds(getX() + 200, getY(),  getIcon().getIconWidth(), getIcon().getIconHeight());
        Game.removeZombie(this);
        int delay = 1000;
        if (getClass() == Football.class) delay = 700;
        if (getClass() == Newspaper.class) delay = 1500;
        Timer t = new Timer(delay, e -> {
            this.setIcon(null);
            c.remove(this);
            new Timer(500, ee -> {
                c.repaint();
                ((Timer)ee.getSource()).stop();
            }).start();

        });
        t.setRepeats(false);
        t.start();
    }
}
