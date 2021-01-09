package Objects.Plants;

import Menus.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PeaShooter extends Plant {
    public static ArrayList<PeaShooter> peaShooters = new ArrayList<>();

    public PeaShooter(Container c) {
        super(c, 100, 1);
        ready = new ImageIcon("gfx/pea.pvz");
        setIcon(ready);
        setLocation(100, 1000);
        PeaShooter.peaShooters.add(this);
    }
}
