package Objects.Zombies;
import Miscs.Icons;
import Miscs.Sounds;
import javax.swing.ImageIcon;
import java.awt.Container;

/**
 * Football class
 * its health is 1000 and speed 6 and damage 15
 * @author RojinaKashefi && HliahashemiPOur
 */
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

    /**
     * LossHealth Method
     * @param Amount as amount it will decrease from health of zombies
     * @param isFrozen is frozen if pea is frozen
     */
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
