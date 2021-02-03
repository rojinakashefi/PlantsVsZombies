package Miscs;

import java.awt.*;

/**
 * This class sets the situation of each objects.
 * @author Hashemipour
 * @since 2021
 */
public class GameObjects {
    public String objectName;
    public int health;
    public Point position;

    /**
     * Main constructor of the GameObjects.class
     * @param objectName object name.
     * @param position   the position of each object.
     * @param health     the number of health.
     */
    public GameObjects(String objectName, Point position, int health) {
        this.objectName = objectName;
        this.position = position;
        this.health = health;
    }
}
