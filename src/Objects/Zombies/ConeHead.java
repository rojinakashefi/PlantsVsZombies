package Objects.Zombies;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConeHead extends Zombie {
    public static ArrayList<Zombie> ConeHead = new ArrayList<>();

    public ConeHead(Container c, int row) {
        super(c, 560, 2, 30, row);
        walk = new ImageIcon("gfx/cone.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
        sizeX = 100;
        sizeY = 100;
        hitSound = Sounds.PLASTIC;
    }
    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
