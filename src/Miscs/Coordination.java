package Miscs;

import Objects.Plants.Plant;
import Objects.Zombies.Zombie;

public class Coordination {
    public int[] coordination = new int[2];
    public Plant plant;
    public Zombie zombie;
    public int type = 1;

    /**
     * Saves the plant position
     * @param plant the plant
     * @param slutX Horizontal position of the plant as Slut
     * @param slutY Vertical position of the plant as Slut
     */
    public Coordination(Plant plant, int slutX, int slutY) {
        coordination[0] = slutX;
        coordination[1] = slutY;
        type = 0;
        this.zombie = null;
        this.plant = plant;
    }

    /**
     * Saves the plant position
     * @param zombie the plant
     * @param slutY Vertical position of the plant as Slut
     */
    public Coordination(Zombie zombie, int slutY) {
        coordination[1] = slutY;
        this.plant = null;
        this.zombie = zombie;
    }
}
