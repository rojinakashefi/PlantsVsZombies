package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;

/**
 * This class represents the Cherry conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class Cherry extends Plant {
    /**
     * Main constructor of the Cherry.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public Cherry(Container c, int[] slut) {
        super(c, 70, 3, slut[0], slut[1], 150);
        ready = new ImageIcon("gfx/cherry.pvz");
        setIcon(ready);
    }
}
