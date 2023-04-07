package worldSimulator.animals;

import worldSimulator.World;
import worldSimulator.Animal;

public class Wolf extends Animal {

    public Wolf(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Wilk";
        this.strength = 9;
        this.initiative = 5;
    }


}
