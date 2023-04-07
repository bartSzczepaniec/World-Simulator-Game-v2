package worldSimulator.plants;

import worldSimulator.Plant;
import worldSimulator.World;

import java.util.Random;

public class Sonchus extends Plant {
    public Sonchus(int x, int y, World world, int birtTurn)
    {
        super(x, y, world, birtTurn);
        this.species = "Mlecz";
        this.strength = 0;
        this.initiative = 0;
    }

    @Override
    public void action()
    {
        for(int i=0;i<3;i++)
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
                //losowanie z dostępnych kierunków
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
                Sonchus newPlant = new Sonchus(newX,newY,this.world,this.world.getTurn());
                this.world.putOrganism(newPlant,newX,newY);
            }

        }
    }
}
