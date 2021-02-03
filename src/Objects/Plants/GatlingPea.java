package Objects.Plants;

import javax.swing.*;
import java.awt.*;
/**
 * This class represents the GatlingPea conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class GatlingPea extends Plant{
    /**
     * Main constructor of the GatlingPea.class
     * @param c the container
     * @param slut the position of this plant.
     */
    public GatlingPea (Container c, int[] slut) {
        super(c, 150, 3, slut[0], slut[1], 225);
        ready = new ImageIcon("gfx/gatPea.pvz");
        setIcon(ready);
    }
}
