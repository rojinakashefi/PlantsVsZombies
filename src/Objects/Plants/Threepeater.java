package Objects.Plants;

import javax.swing.*;
import java.awt.*;

public class Threepeater extends Plant{
    public Threepeater (Container c, int[] slut) {
        super(c, 200, 1, slut[0], slut[1], 325);
        ready = new ImageIcon("gfx/threepeater.pvz");
        setIcon(ready);
    }

}
