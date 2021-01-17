package Objects.Zombies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Zombie extends JLabel {
    int health;
    int speed;
    int damage;
    ImageIcon walk, die;
    public static ArrayList<Zombie> zombies = new ArrayList<>();

    public Zombie(Container c, int health, int speed, int damage) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        Zombie.zombies.add(this);
        c.add(this);
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
}
