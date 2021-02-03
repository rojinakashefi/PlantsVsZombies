package Objects.Zombies;
import Miscs.Icons;
import Miscs.Sounds;
import javax.swing.*;
import java.awt.*;

/**
 * Newspaper class
 * it consists of 350 health speed of 2 and damage of 10
 * @author RojinaKashefi && HeliaHashemiPour
 */
public class Newspaper extends Zombie{
    ImageIcon normWalk = new ImageIcon("gfx/newsBWalk.pvz");

    public Newspaper(Container c, int row) {
        super(c, 350, 2, 10, row);
        walk = Icons.newsPaperIcon;
        die = new ImageIcon("gfx/dieNews.pvz");
        setIcon(walk);
        sizeX = 246;
        sizeY = 216;
    }

    /**
     * Loss Health method
     * @param Amount as amount it will decrease from health of zombies
     * @param isFrozen is frozen if pea is frozen
     */
    @Override
    public void lossHealth(int Amount, boolean isFrozen) {
        health -= Amount;
        if (health > 0) {
            if(health >= 200) Sounds.play(Sounds.PAPER);
            else {
                Sounds.play(Sounds.NONE);
                if (this.getIcon() != normWalk) {
                    this.setIcon(normWalk);
                    Sounds.play(Sounds.NEWSPAPER_GRUNT);
                    this.speed = 4;
                }
            }
        } else {
            kill(false);
        }
        if (isFrozen && !frozen) {
            Sounds.play(Sounds.FREEZE);
            speed /= 2;
            frozen = true;
        }
    }
}
