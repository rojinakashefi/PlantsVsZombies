package Objects.Zombies;

import Menus.Game;
import Miscs.Sounds;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Container;


public class Normal extends Zombie {

    public Normal(Container c, int row) {
        super(c, 200, 2, 30, row);
        walk = new ImageIcon("gfx/normal.pvz");
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {
    }
}
