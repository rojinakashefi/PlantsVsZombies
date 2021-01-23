package Objects.Zombies;

import Miscs.Sounds;

import javax.swing.ImageIcon;
import java.awt.Container;

public class BucketHead extends Zombie {
    ImageIcon normWalk = new ImageIcon("gfx/normal.pvz");

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
            speed /= 2;
            frozen = true;
            Sounds.play(Sounds.FREEZE);
        }
    }
}
