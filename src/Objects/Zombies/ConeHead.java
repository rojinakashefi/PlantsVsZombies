package Objects.Zombies;

import Miscs.Sounds;

import javax.swing.ImageIcon;
import java.awt.Container;

public class ConeHead extends Zombie {
    ImageIcon normWalk = new ImageIcon("gfx/normal.pvz");

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
        health -= Amount;
        if (health > 100) {
            Sounds.play(hitSound);
        }
        else {
            if (health <= 0) {
                kill(false);
            } else {
                this.setIcon(normWalk);
                this.sizeX = 81;
                this.sizeY = 131;
            }
        }
        if (isFrozen && !frozen) {
            Sounds.play(Sounds.FREEZE);
            speed /= 2;
            frozen = true;
        }
    }
}
