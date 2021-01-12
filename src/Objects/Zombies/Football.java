package Objects.Zombies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Football extends Zombie {
    public static ArrayList<Zombie> ConeHead = new ArrayList<>();

    public Football(Container c) {
        super(c, 100, 1, 30);
        walk = new ImageIcon("gfx/football.pvz");
        die = new ImageIcon("gfx/footDead.pvz");
        setIcon(walk);
        Normal.zombies.add(this);
    }
}
