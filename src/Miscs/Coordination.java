package Miscs;

import Objects.Plants.Plant;
import Objects.Zombies.Zombie;

/**
 * Coordination Saves the plants or zombies positions.
 * @author Hashemipour
 * @since 2021
 */
public class Coordination {
    public int[] coordination = new int[2];
    public Plant plant;
    public Zombie zombie;
    public int type = 1;

    /**
     * Saves the plant position
     * @param plant the plant
     * @param X  Horizontal position of the plant .
     * @param Y  Vertical position of the plant .
     */
    public Coordination(Plant plant, int X, int Y) {
        coordination[0] = X;
        coordination[1] = Y;
        type = 0;
        this.zombie = null;
        this.plant = plant;
    }

    /**
     * Saves the zombie position
     * @param zombie the zombie
     * @param Y     Vertical position of the zombie .
     */
    public Coordination(Zombie zombie, int Y) {
        coordination[1] = Y;
        this.plant = null;
        this.zombie = zombie;
    }
}
