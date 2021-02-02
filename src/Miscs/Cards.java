package Miscs;

import javax.swing.JLabel;
import java.awt.Container;

import static Miscs.Icons.*;

public class Cards {
    public static final int SNOW_PEA = 0, PEA_SHOOTER = 1, SUNFLOWER = 2, WALL_NUT = 3, CHERRY = 4, REPEATER = 5
            , THREEPEATER = 6, GATLING = 7, POTATO = 8;

    public static JLabel getCard(int number, Container c) {
        JLabel tempLabel = new JLabel();
        switch (number) {
            case SNOW_PEA -> tempLabel.setIcon(snowPeaCard);
            case PEA_SHOOTER -> tempLabel.setIcon(peaShooterCard);
            case SUNFLOWER -> tempLabel.setIcon(sunflowerCard);
            case WALL_NUT -> tempLabel.setIcon(wallCard);
            case CHERRY -> tempLabel.setIcon(cherryCard);
            case GATLING -> tempLabel.setIcon(gatlingCard);
            case THREEPEATER -> tempLabel.setIcon(threePeaCard);
            case REPEATER -> tempLabel.setIcon(repeaterCard);
            case POTATO -> tempLabel.setIcon(potatoCard);
            default -> throw new RuntimeException("Wrong Input in Cards Class");
        }
        c.add(tempLabel);
        return tempLabel;
    }
}
