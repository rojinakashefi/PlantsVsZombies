package Objects.Plants;

import java.awt.Container;
import javax.swing.ImageIcon;
/**
 * This class represents the SunFlower conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class SunFlower extends Plant {
    /**
     * Main constructor of the SunFlower.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public SunFlower(Container c, int[] slut) {
        super(c, 50, 20, slut[0], slut[1], 50);
        ready = new ImageIcon("gfx/sunflower.pvz");
        die = new ImageIcon("gfx/sunDie.pvz");
        setIcon(ready);
        setLocation(100, 1000);
    }
}
