package Objects.Zombies;
import Menus.Game;
import Miscs.Icons;
import Miscs.Sounds;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Container;

/**
 * It normal zombie Class
 * it health is 200 and it speed is 2 (0.5 per second)it damages is 5
 */
public class Normal extends Zombie {

    public Normal(Container c, int row) {
        super(c, 200, 2, 5, row);
        walk = Icons.normalZombie;
        die = new ImageIcon("gfx/normDead.pvz");
        setIcon(walk);
    }

    /**
     * LossHealth method
     * @param Amount as amount it will decrease from health of zombies
     * @param isFrozen is frozen if pea is frozen
     */
    @Override
    public void lossHealth(int Amount, boolean isFrozen) {
        health -= Amount;
        if (isFrozen && !frozen) {
            speed /= 2;
            frozen = true;
            Sounds.play(Sounds.FREEZE);
        }
        if (health > 0) {
            Sounds.play(hitSound);
        }
        else {
            kill(false);
        }
    }
}
