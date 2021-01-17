package Objects.Zombies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Normal extends Zombie {
    public static ArrayList<Zombie> zombies = new ArrayList<>();

    public Normal(Container c) {
        super(c, 100, 1, 30);
        walk = new ImageIcon("gfx/normal.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
        Normal.zombies.add(this);
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
