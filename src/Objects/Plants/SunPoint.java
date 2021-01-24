package Objects.Plants;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Container;

public class SunPoint extends JLabel {
    public final int points = 25;

    public SunPoint(Container c) {
        this.setIcon(new ImageIcon("gfx/sun.pvz"));
        c.add(this);
    }
    public void remove() {
        System.runFinalization();
    }
}
