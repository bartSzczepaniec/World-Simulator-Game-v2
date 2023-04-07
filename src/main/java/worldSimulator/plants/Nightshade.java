package worldSimulator.plants;

import worldSimulator.Organism;
import worldSimulator.Plant;
import worldSimulator.World;

import java.util.List;

public class Nightshade extends Plant {
    public Nightshade(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Wilczejagody";
        this.strength = 99;
        this.initiative = 0;
    }

    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        actionOrganisms.remove(this);
        this.world.addComment(this.species + " zatruwa " + attacker.getSpecies() + " na polu: " + this.x + " " + this.y);
        this.world.removeOrganism(pX,pY);
        this.world.removeOrganism(this.x,this.y);
    }
}
