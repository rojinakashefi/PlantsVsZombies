package Objects.Zombies;

import javax.swing.*;
import java.awt.*;

public class Newspaper extends Zombie{
    ImageIcon normWalk = new ImageIcon("gfx/newsBWalk.pvz");

    public Newspaper(Container c, int row) {
        super(c, 350, 2, 10, row);
        walk = new ImageIcon("gfx/newsWalk.pvz");
        die = new ImageIcon("gfx/dieNews.pvz");
        setIcon(walk);
        sizeX = 246;
        sizeY = 216;
    }
    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
