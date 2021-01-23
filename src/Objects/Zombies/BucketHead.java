package Objects.Zombies;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BucketHead extends Zombie {
    public static ArrayList<Zombie> BucketHead = new ArrayList<>();

    public BucketHead(Container c) {
        super(c, 100, 1, 30);
        walk = new ImageIcon("gfx/bucket.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
        Normal.zombies.add(this);
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
