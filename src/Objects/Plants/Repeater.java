package Objects.Plants;

import Miscs.Icons;

import javax.swing.ImageIcon;
import java.awt.Container;
/**
 * This class represents the Repeater conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class Repeater extends Plant {
    /**
     * Main constructor of the Repeater.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public Repeater (Container c, int[] slut) {
        super(c, 150, 2, slut[0], slut[1], 200);
        ready = new ImageIcon("gfx/repeater.pvz");
        setIcon(ready);
    }
}
