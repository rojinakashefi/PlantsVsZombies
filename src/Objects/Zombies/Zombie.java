package Objects.Zombies;

import Menus.Game;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Zombie extends JLabel {
    Container c;
    boolean frozen = false;
    public int row;
    public int health;
    public int speed;
    int damage;
    ImageIcon walk, die, burned = new ImageIcon("gfx/burned.pvz");
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

    private void checkHealth(Zombie subject, Timer timer) {
        if (health < 0) {
            timer.stop();
            setIcon(die);
            String name = getClass().getName();
            System.out.println(name);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkHealth(subject, timer);
        }
    }
    public abstract void lossHealth(int Amount, boolean isFrozen);
    public void kill(boolean burn) {
        Sounds.play(hitSound);
        this.setIcon(burn?burned:die);
        Game.removeZombie(this);
        Timer t = new Timer(1000, e -> c.remove(this));
        t.setRepeats(false);
        t.start();
    }
}