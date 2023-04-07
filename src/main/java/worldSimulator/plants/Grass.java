package worldSimulator.plants;

import worldSimulator.Plant;
import worldSimulator.World;

public class Grass extends Plant {
    public Grass(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Trawa";
        this.strength = 0;
        this.initiative = 0;
    }
}
