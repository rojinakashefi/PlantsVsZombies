package Objects.Plants;

import javax.swing.*;
import java.awt.*;
public class GatlingPea extends Plant{

    public GatlingPea (Container c, int[] slut) {
        super(c, 150, 3, slut[0], slut[1], 250);
        ready = new ImageIcon("gfx/gatPea.pvz");
        setIcon(ready);
    }
}
