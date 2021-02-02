package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;

public class PeaShooter extends Plant {

    public PeaShooter(Container c, int[] slut) {
        super(c, 70, 1, slut[0], slut[1], 100);
        ready = new ImageIcon("gfx/pea.pvz");
        setIcon(ready);
    }
}
