package Objects.Plants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Repeater extends Plant {
    public static ArrayList<Repeater> repeaters = new ArrayList<>();

    public Repeater (Container c) {
        super(c, 100, 2);
        ready = new ImageIcon("gfx/repeater.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        Repeater.repeaters.add(this);
    }
}
