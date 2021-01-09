package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Wall_nut extends Plant {
    public static ArrayList<Wall_nut> wall_nuts = new ArrayList<>();

    public Wall_nut(Container c) {
        super(c, 100, 0);
        ready = new ImageIcon("gfx/nut_1.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        Wall_nut.wall_nuts.add(this);
    }
}

