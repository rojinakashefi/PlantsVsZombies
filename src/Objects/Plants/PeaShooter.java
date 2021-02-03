package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;
/**
 * This class represents the PeaShooter conditions.
 * We sets the health , speed, position , the cost and set the Image of this plant .
 * @author Hashemipour
 * @since 2021
 */
public class PeaShooter extends Plant {
    /**
     * Main constructor of the PeaShooter.class.
     * @param c the container.
     * @param slut the position of this plant.
     */
    public PeaShooter(Container c, int[] slut) {
        super(c, 70, 1, slut[0], slut[1], 100);
        ready = new ImageIcon("gfx/pea.pvz");
        setIcon(ready);
    }
}
