package worldSimulator.animals;

import worldSimulator.Animal;
import worldSimulator.Organism;
import worldSimulator.World;

import java.util.Random;

public class Fox extends Animal {
    public Fox(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Lis";
        this.strength = 3;
        this.initiative = 7;
    }

    @Override
    public void action()
    {
        int[] directions = new int[4];
        int available_directions = 0; // sprawdzenie sasiadow, w poszukiwaniu wolnego miejsca lub takiego na ktorym stoi slabszy organizm
        Random random = new Random();
        Organism neighbour=null;
        if (this.getY() > 0)
        {
            // góra TAK
            //sasiad = this.swiat.getOrganizm(this.getX(), this.getY() - 1);
            neighbour = this.world.getOrganismsMap()[this.getY()-1][this.getX()];
            if (neighbour == null)
                directions[available_directions++] = 0;
            else if(neighbour.getStrength()<=this.strength)
                directions[available_directions++] = 0;
        }
        if (this.getY() < this.world.getN() - 1)
        {
            // dół TAK
            neighbour = this.world.getOrganismsMap()[this.getY()+1][this.getX()];
            if (neighbour == null)
                directions[available_directions++] = 1;
            else if (neighbour.getStrength() <= this.strength)
                directions[available_directions++] = 1;
        }
        if (this.getX() > 0)
        {
            //lewo TAK
            neighbour = this.world.getOrganismsMap()[this.getY()][this.getX()-1];
            if (neighbour == null)
                directions[available_directions++] = 2;
            else if (neighbour.getStrength() <= this.strength)
                directions[available_directions++] = 2;
        }
        if (this.getX() < this.world.getM() - 1)
        {
            //prawo TAK
            neighbour = this.world.getOrganismsMap()[this.getY()][this.getX()+1];
            if (neighbour == null)
                directions[available_directions++] = 3;
            else if (neighbour.getStrength() <= this.strength)
                directions[available_directions++] = 3;
        }
        if (available_directions == 0)
            return;

        //losowanie z dostępnych kierunków
        int direction_index = random.nextInt(available_directions);
        if (directions[direction_index] == 0)
            this.setY(this.getY() - 1);
        if (directions[direction_index] == 1)
            this.setY(this.getY() + 1);
        if (directions[direction_index] == 2)
            this.setX(this.getX() - 1);
        if (directions[direction_index] == 3)
            this.setX(this.getX() + 1);
    }
}
