package Objects.Zombies;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BucketHead extends Zombie {
    public static ArrayList<Zombie> BucketHead = new ArrayList<>();

    public BucketHead(Container c, int row) {
        super(c, 1100, 3, 30, row);
        walk = new ImageIcon("gfx/bucket.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        sizeX = 36;
        sizeY = 62;
        setIcon(walk);
        hitSound = Sounds.METAL;
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
