package worldSimulator.plants;

import worldSimulator.Organism;
import worldSimulator.Plant;
import worldSimulator.World;

import java.util.List;

public class Guarana extends Plant {
    public Guarana(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Guarana";
        this.strength = 0;
        this.initiative = 0;
    }

    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        actionOrganisms.remove(this);
        attacker.setStrength(attacker.getStrength() + 3);
        this.world.addComment(attacker.getSpecies() + " zjada " + this.species + " na polu: " + this.x + " " + this.y + " i zwiekszyl sile do " + attacker.getStrength());
        this.world.replaceOrganism(pX,pY,this.x,this.y);
    }
}
