package Objects.Zombies;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Football extends Zombie {
    public static ArrayList<Zombie> ConeHead = new ArrayList<>();

    public Football(Container c, int row) {
        super(c, 700, 6, 30, row);
        walk = new ImageIcon("gfx/football.pvz");
        die = new ImageIcon("gfx/footDead.pvz");
        sizeX = 106;
        sizeY = 126;
        setIcon(walk);
        hitSound = Sounds.PLASTIC;
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
