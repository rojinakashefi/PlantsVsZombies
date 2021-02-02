package Miscs;

import java.awt.*;

public class GameObjects {
    public String objectName;
    public int health;
    public Point position;

    public GameObjects(String objectName, Point position, int health) {
        this.objectName = objectName;
        this.position = position;
        this.health = health;
    }
}
