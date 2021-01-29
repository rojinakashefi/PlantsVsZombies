package Objects.Zombies;

import Miscs.Sounds;

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
        if (health - Amount > 165) Sounds.play(Sounds.NEWSPAPER_GRUNT);
        health -= Amount;
        if (health > 0) {
            if(health >= 200) Sounds.play(Sounds.PAPER);
            else {
                Sounds.play(Sounds.NONE);
                this.setIcon(normWalk);
            }
        }
        if (isFrozen && !frozen) {
            Sounds.play(Sounds.FREEZE);
            speed /= 2;
            frozen = true;
        }
    }
}
