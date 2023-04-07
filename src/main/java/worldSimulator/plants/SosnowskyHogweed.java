package worldSimulator.plants;

import worldSimulator.*;
import worldSimulator.animals.CyberSheep;

import java.util.List;

public class SosnowskyHogweed extends Plant {
    public SosnowskyHogweed(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Barszczsosnowskiego";
        this.strength = 10;
        this.initiative = 0;
    }

    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        actionOrganisms.remove(this);
        if(attacker instanceof CyberSheep) // napastnik wygrywa
        {
            this.world.addComment(attacker.getSpecies() + " zjada " + this.species + " na polu: " + this.x + " " + this.y);
            this.world.replaceOrganism(pX,pY,this.x,this.y);
        }
        else
            { // napastnik przegrywa
            this.world.addComment(this.species + " zatruwa " + attacker.getSpecies() + " na polu: " + this.x + " " + this.y);
            this.world.removeOrganism(pX,pY);
            this.world.removeOrganism(this.x,this.y);
        }
    }

    @Override
    public void action() {
        Organism neighbour=null;
        if(this.y > 0)
        {
            neighbour = this.world.getOrganismsMap()[this.y - 1][this.x];
            if(neighbour != null && neighbour instanceof Animal && !(neighbour instanceof CyberSheep))
            {
                this.world.addComment(this.species + " zabija (na około) " + neighbour.getSpecies() + " na polu: " + neighbour.getX() + " " + neighbour.getY());
                this.world.removeOrganism(neighbour.getX(), neighbour.getY());
            }
        }
        if(this.y < this.world.getN() - 1)
        {
            neighbour = this.world.getOrganismsMap()[this.y + 1][this.x];
            if (neighbour != null && neighbour instanceof Animal && !(neighbour instanceof CyberSheep)) {
                this.world.addComment(this.species + " zabija (na około) " + neighbour.getSpecies() + " na polu: " + neighbour.getX() + " " + neighbour.getY());
                this.world.removeOrganism(neighbour.getX(), neighbour.getY());
            }
        }
        if(this.x > 0)
        {
            neighbour = this.world.getOrganismsMap()[this.y][this.x - 1];
            if (neighbour != null && neighbour instanceof Animal && !(neighbour instanceof CyberSheep)) {
                this.world.addComment(this.species + " zabija (na około) " + neighbour.getSpecies() + " na polu: " + neighbour.getX() + " " + neighbour.getY());
                this.world.removeOrganism(neighbour.getX(), neighbour.getY());
            }
        }
        if(this.getX() < this.world.getM() - 1)
        {
            neighbour = this.world.getOrganismsMap()[this.y][this.x + 1];
            if (neighbour != null && neighbour instanceof Animal && !(neighbour instanceof CyberSheep)) {
                this.world.addComment(this.species + " zabija (na około) " + neighbour.getSpecies() + " na polu: " + neighbour.getX() + " " + neighbour.getY());
                this.world.removeOrganism(neighbour.getX(), neighbour.getY());
            }
        }
        super.action();
    }
}
