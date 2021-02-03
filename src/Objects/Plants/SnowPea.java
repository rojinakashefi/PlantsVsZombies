package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;
/**
 * This class represents the SnowPea conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class SnowPea extends Plant {
    /**
     * Main constructor of the SnowPea.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public SnowPea(Container c, int[] slut) {
        super(c, 100, 1, slut[0], slut[1], 175);
        ready = new ImageIcon("gfx/snowPea.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        Plant.plants.add(this);
    }
}
