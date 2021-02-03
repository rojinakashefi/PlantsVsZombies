package Objects.Plants;

import javax.swing.*;
import java.awt.*;
/**
 * This class represents the Threepeater conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class Threepeater extends Plant{
    /**
     * Main constructor of the Threepeater.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public Threepeater (Container c, int[] slut) {
        super(c, 200, 1, slut[0], slut[1], 325);
        ready = new ImageIcon("gfx/threepeater.pvz");
        setIcon(ready);
    }

}
