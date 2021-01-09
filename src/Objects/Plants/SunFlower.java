package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SunFlower extends Plant {
    public static ArrayList<SunFlower> sunFlowers = new ArrayList<>();

    public SunFlower(Container c) {
        super(c, 100, 20);
        ready = new ImageIcon("gfx/sunflower.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        SunFlower.sunFlowers.add(this);
    }
}
