package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cherry extends Plant {
    public static ArrayList<Cherry> cherries = new ArrayList<>();

    public Cherry(Container c) {
        super(c, 100, 3);
        ready = new ImageIcon("gfx/cherry.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        Cherry.cherries.add(this);
    }
}
