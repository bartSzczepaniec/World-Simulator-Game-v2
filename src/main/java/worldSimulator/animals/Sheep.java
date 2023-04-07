package worldSimulator.animals;

import worldSimulator.World;
import worldSimulator.Animal;

public class Sheep extends Animal {

    public Sheep(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Owca";
        this.strength = 4;
        this.initiative = 4;
    }

}
