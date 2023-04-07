package worldSimulator.animals;

import worldSimulator.Organism;
import worldSimulator.World;
import worldSimulator.Animal;

import java.util.List;
import java.util.Random;

public class Turtle extends Animal {
    public Turtle(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Zolw";
        this.strength = 2;
        this.initiative = 1;
    }

    @Override
    public void action()
    {
        Random random = new Random();
        if (random.nextInt(4) == 0)
        {
            super.action();
        }
    }

    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        if(attacker instanceof Turtle)
        {
            super.collision(attacker, pX, pY, actionOrganisms);
            return;
        }
        else if (attacker.getStrength() < 5)
        { // napastnik przegrywa
            this.world.addComment(this.species + " odstrasza " + attacker.getSpecies() + " z pola: " + this.x + " " + this.y);
            attacker.setX(pX);
            attacker.setY(pY);
        }
        else // napastnik wygrywa
        {
            actionOrganisms.remove(this);

            this.world.addComment(attacker.getSpecies() + " zabija " + this.species + " na polu: " + this.x + " " + this.y);
            this.world.replaceOrganism(pX,pY,this.x,this.y);
        }
    }
}
