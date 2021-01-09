package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SnowPea extends Plant {
    public static ArrayList<SnowPea> snowPeas = new ArrayList<>();

    public SnowPea(Container c) {
        super(c, 100, 1);
        ready = new ImageIcon("gfx/snowPea.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        SnowPea.snowPeas.add(this);
    }
}
