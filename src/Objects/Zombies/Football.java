package Objects.Zombies;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.ImageIcon;
import java.awt.Container;

public class Football extends Zombie {

    public Football(Container c, int row) {
        super(c, 1000, 6, 15, row);
        walk = Icons.footballIcon;
        die = new ImageIcon("gfx/footDead.pvz");
        sizeX = 106;
        sizeY = 126;
        setIcon(walk);
        hitSound = Sounds.PLASTIC;
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {
        health -= Amount;
        if (health > 0) {
            Sounds.play(hitSound);
        }
        else {
            kill(false);
        }
        if (isFrozen && !frozen) {
            speed /= 2;
            frozen = true;
            Sounds.play(Sounds.FREEZE);
        }
    }
}
