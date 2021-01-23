package Objects.Plants;

import java.awt.Container;
import javax.swing.ImageIcon;


public class SunFlower extends Plant {

    public SunFlower(Container c, int[] slut) {
        super(c, 100, 20, slut[0], slut[1], 50);
        ready = new ImageIcon("gfx/sunflower.pvz");
        die = new ImageIcon("gfx/sunDie.pvz");
        setIcon(ready);
        setLocation(100, 1000);
    }
}
