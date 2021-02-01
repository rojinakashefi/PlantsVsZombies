package Objects.Plants;

import javax.swing.ImageIcon;
import java.awt.Container;

public class Cherry extends Plant {

    public Cherry(Container c, int[] slut) {
        super(c, 70, 3, slut[0], slut[1], 150);
        ready = new ImageIcon("gfx/cherry.pvz");
        setIcon(ready);
    }
}
