package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;


public class SnowPea extends Plant {

    public SnowPea(Container c, int[] slut) {
        super(c, 100, 1, slut[0], slut[1], 175);
        ready = new ImageIcon("gfx/snowPea.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        Plant.plants.add(this);
    }
}
