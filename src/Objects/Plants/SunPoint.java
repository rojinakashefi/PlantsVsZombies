package Objects.Plants;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Container;

/**
 *This method performed the sun points that the player can collects them.
 * @author Hashemipour
 * @since 2021
 */
public class SunPoint extends JLabel {
    public final int points = 25;//the point of each sun

    /**
     * Main constructor of the SunPoint.class
     * @param c the container
     */
    public SunPoint(Container c) {
        this.setIcon(new ImageIcon("gfx/sun.pvz"));
        c.add(this);
    }

    /**
     *This method remove completely.
     */
    public void remove() {
        System.runFinalization();
    }
}
