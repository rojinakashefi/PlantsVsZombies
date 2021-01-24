package Miscs;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Container;

public class Cards {
    public static final int SNOW_PEA = 0, PEA_SHOOTER = 1, SUNFLOWER = 2, WALL_NUT = 3, CHERRY = 4;
    static final ImageIcon snowPea = new ImageIcon("gfx/cardFreeze.pvz");
    static final ImageIcon peaShooter = new ImageIcon("gfx/cardPea.pvz");
    // static final ImageIcon repeater = new ImageIcon("gfx/cardFreeze.pvz");
    static final ImageIcon sunflower = new ImageIcon("gfx/cardSun.pvz");
    static final ImageIcon wall = new ImageIcon("gfx/cardWallnut.pvz");
    static final ImageIcon cherry = new ImageIcon("gfx/cardCherry.pvz");

    public static JLabel getCard(int number, Container c) {
        JLabel tempLabel = new JLabel();
        switch (number) {
            case SNOW_PEA -> tempLabel.setIcon(snowPea);
            case PEA_SHOOTER -> tempLabel.setIcon(peaShooter);
            case SUNFLOWER -> tempLabel.setIcon(sunflower);
            case WALL_NUT -> tempLabel.setIcon(wall);
            case CHERRY -> tempLabel.setIcon(cherry);
            default -> throw new RuntimeException("Wrong Input in Cards Class");
        }
        c.add(tempLabel);
        return tempLabel;
    }
}
