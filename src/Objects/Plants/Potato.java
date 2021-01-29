package Objects.Plants;

import javax.swing.*;
import java.awt.*;

public class Potato extends Plant{

    public Potato (Container c, int[] slut) {
        super(c, 100, 2, slut[0], slut[1], 25);
        ready = new ImageIcon("gfx/potato_1.pvz");
        setIcon(ready);
    }
}
