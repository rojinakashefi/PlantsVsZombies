package Objects.Zombies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConeHead extends Zombie {
    public static ArrayList<Zombie> ConeHead = new ArrayList<>();

    public ConeHead(Container c) {
        super(c, 100, 1, 30);
        walk = new ImageIcon("gfx/cone.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
        Normal.zombies.add(this);
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
