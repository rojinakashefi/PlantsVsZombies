package Objects.Zombies;
import Menus.Game;
import Miscs.Sounds;
import javax.swing.*;
import java.awt.Container;
import java.util.ArrayList;

/**
 * Its superclass of all zombies
 * Contains of lossHealth method which for each zombie is different
 * Contains of kill method which is same for all zombies
 */
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

    /**
     * Zombie Constructor
     * @param c as the container we are adding zombies to
     * @param health as health of zombie
     * @param speed as speed of zombies
     * @param damage as damage they have when a plants shoot them
     * @param row as row of zombies
     */
    public Zombie(Container c, int health, int speed, int damage, int row) {
        this.c = c;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        Zombie.zombies.add(this);
        c.add(this);
        this.row = row;
    }

    /**
     * Loss Health method
     * @param Amount as amount it will decrease from health of zombies
     * @param isFrozen is frozen if pea is frozen
     */
    public abstract void lossHealth(int Amount, boolean isFrozen);

    /**
     * Killing zombies (mitual in all zombies)
     * @param burn if the way of thier kill is burn or not if burn set burn icon if not dont
     */
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
            //for removing them in game
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
