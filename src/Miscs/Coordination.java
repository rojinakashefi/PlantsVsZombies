package Miscs;

import Objects.Plants.Plant;
import Objects.Zombies.Zombie;

public class Coordination {
    public int[] coordination = new int[2];
    public Plant plant;
    public Zombie zombie;

    public Coordination(Plant plant, int slutX, int slutY) {
        coordination[0] = slutX;
        coordination[1] = slutY;
        this.plant = plant;
    }

    public Coordination(Zombie zombie, int slutY) {
        coordination[1] = slutY;
        this.zombie = zombie;
    }
}
