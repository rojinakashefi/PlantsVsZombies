package Objects.Plants;

import Miscs.Icons;

import javax.swing.ImageIcon;
import java.awt.Container;

public class Repeater extends Plant {

    public Repeater (Container c, int[] slut) {
        super(c, 150, 2, slut[0], slut[1], 200);
        ready = new ImageIcon("gfx/repeater.pvz");
        setIcon(ready);
    }
}
