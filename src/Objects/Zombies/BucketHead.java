package Objects.Zombies;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.ImageIcon;
import java.awt.Container;

public class BucketHead extends Zombie {
    ImageIcon normWalk = new ImageIcon("gfx/normal.pvz");

    public BucketHead(Container c, int row) {
        super(c, 1100, 2, 20, row);
        walk = Icons.bucketHeadIcon;
        die = new ImageIcon("gfx/normDead.pvz");
        sizeX = 166;
        sizeY = 144;
        setIcon(walk);
        hitSound = Sounds.METAL;
    }

    @Override
    public void lossHealth(int Amount, boolean isFrozen) {
        health -= Amount;
        if (health > 200) {
            Sounds.play(hitSound);
        }
        else {
            if (health <= 0) {
                kill(false);
            } else {
                this.setIcon(normWalk);
                this.sizeX = 81;
                this.sizeY = 131;
                Sounds.play(Sounds.NONE);
            }
        }
        if (isFrozen && !frozen) {
            speed /= 2;
            frozen = true;
            Sounds.play(Sounds.FREEZE);
        }
    }
}
