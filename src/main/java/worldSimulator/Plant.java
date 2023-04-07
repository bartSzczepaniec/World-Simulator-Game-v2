package worldSimulator;

import worldSimulator.plants.*;

import java.util.List;
import java.util.Random;

public class Plant extends Organism {
    public Plant(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
    }

    @Override
    public void action()
    {
        final int PLANTCHANCE = 100;
        Random random = new Random();
        int randomization = random.nextInt(PLANTCHANCE);
        if(randomization % 10 == 0)
        {
            int[] directions = new int[4];
            int available_directions = 0; // sprawdzenie wolnych kierunkow do zasiania sie
            if (this.y > 0)
            {
                // góra TAK
                if(this.world.getOrganismsMap()[this.y-1][this.x] == null)
                    directions[available_directions++] = 0;
            }
            if (this.y < this.world.getN()-1)
            {
                // dół TAK
                if(this.world.getOrganismsMap()[this.y+1][this.x] == null)
                    directions[available_directions++] = 1;
            }
            if (this.x > 0)
            {
                //lewo TAK
                if(this.world.getOrganismsMap()[this.y][this.x-1] == null)
                    directions[available_directions++] = 2;
            }
            if (this.x < this.world.getM()-1)
            {
                //prawo TAK
                if(this.world.getOrganismsMap()[this.y][this.x+1] == null)
                    directions[available_directions++] = 3;
            }
            if(available_directions==0)
                return;
            //randomization z dostępnych kierunków
            int direction_index = random.nextInt(available_directions);
            int newX=0, newY=0;
            if (directions[direction_index] == 0)
            {
                newX = this.x;
                newY = this.y - 1;
            }
            if (directions[direction_index] == 1)
            {
                newX = this.x;
                newY = this.y + 1;
            }
            if (directions[direction_index] == 2)
            {
                newX = this.x -1;
                newY = this.y;
            }
            if (directions[direction_index] == 3)
            {
                newX = this.x +1;
                newY = this.y;
            }
            //tworzenie nowej rosliny
            world.addComment(this.species + " zasial sie na pole " + newX + " " + newY);
            if(this.species =="Trawa")
            {
                Grass newPlant = new Grass(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }
            if(this.species =="Mlecz")
            {
                Sonchus newPlant = new Sonchus(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }
            if(this.species =="Guarana")
            {
                Guarana newPlant = new Guarana(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }
            if(this.species =="Barszczsosnowskiego")
            {
                SosnowskyHogweed newPlant = new SosnowskyHogweed(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }
            if(this.species =="Wilczejagody")
            {
                Nightshade newPlant = new Nightshade(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }

        }

    }
    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        actionOrganisms.remove(this);
        if (attacker.getStrength() < this.strength)
        { // napastnik przegrywa
            this.world.addComment(this.species + " zatruwa " + attacker.species + " na polu: " + this.x + " " + this.y);
            this.world.removeOrganism(pX,pY);
            this.world.removeOrganism(this.x,this.y);
        }
        else // napastnik wygrywa
        {
            this.world.addComment(attacker.species + " zjada " + this.species + " na polu: " + this.x + " " + this.y);
            this.world.replaceOrganism(pX,pY,this.x,this.y);
        }
    }
}
