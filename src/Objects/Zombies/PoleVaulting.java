package Objects.Zombies;

import javax.swing.*;
import java.awt.*;

public class PoleVaulting extends Zombie {
    public ImageIcon noPole = new ImageIcon("gfx/noPole.pvz");
    public ImageIcon jump1 = new ImageIcon("gfx/jump1.pvz");
    public ImageIcon jump2 = new ImageIcon("gfx/jump2.pvz");
    public PoleVaulting(Container c, int row) {
        super(c, 340, 6, 20, row);
        walk = new ImageIcon("gfx/pole.pvz");
        die = new ImageIcon("gfx/diePole.pvz");
        sizeX = 348;
        sizeY = 212;
        setIcon(walk);
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {

    }
}
