package Objects.Plants;

import javax.swing.*;
import java.awt.*;
/**
 * This class represents the Potato conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class Potato extends Plant{
    /**
     * Main constructor of the Potato.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public Potato (Container c, int[] slut) {
        super(c, 100, 2, slut[0], slut[1], 25);
        ready = new ImageIcon("gfx/potato_1.pvz");
        setIcon(ready);
    }
}
