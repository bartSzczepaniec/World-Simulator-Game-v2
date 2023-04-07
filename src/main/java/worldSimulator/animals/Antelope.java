package worldSimulator.animals;

import worldSimulator.Organism;
import worldSimulator.World;
import worldSimulator.Animal;

import java.util.List;
import java.util.Random;

public class Antelope extends Animal {
    public Antelope(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Antylopa";
        this.strength = 4;
        this.initiative = 4;
    }
    @Override
    public void action()
    {
        int[] directions = new int[8];
        int available_directions = 0; // sprawdzenie wolnych kierunkow do przesuniecia sie
        if (this.y > 0)
        {
            // góra TAK
            directions[available_directions++] = 0;
        }
        if (this.y < this.world.getN()-1)
        {
            // dół TAK
            directions[available_directions++] = 1;
        }
        if (this.x > 0)
        {
            //lewo TAK
            directions[available_directions++] = 2;
        }
        if (this.x < this.world.getM()-1)
        {
            //prawo TAK
            directions[available_directions++] = 3;
        }
        if (this.y - 1 > 0)
        {
            // górax2 TAK
            directions[available_directions++] = 4;
        }
        if (this.y < this.world.getN() - 2)
        {
            // dolx2 TAK
            directions[available_directions++] = 5;
        }
        if (this.x - 1 > 0)
        {
            // lewox2 TAK
            directions[available_directions++] = 6;
        }
        if (this.x < this.world.getM() - 2)
        {
            // prawox2 TAK
            directions[available_directions++] = 7;
        }
        //losowanie z dostępnych kierunków
        Random random = new Random();
        int direction_index = random.nextInt(available_directions);
        if (directions[direction_index] == 0)
            this.setY(this.getY() - 1);
        if (directions[direction_index] == 1)
            this.setY(this.getY() + 1);
        if (directions[direction_index] == 2)
            this.setX(this.getX() - 1);
        if (directions[direction_index] == 3)
            this.setX(this.getX() + 1);
        if (directions[direction_index] == 4)
            this.setY(this.getY() - 2);
        if (directions[direction_index] == 5)
            this.setY(this.getY() + 2);
        if (directions[direction_index] == 6)
            this.setX(this.getX() - 2);
        if (directions[direction_index] == 7)
            this.setX(this.getX() + 2);
    }

    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        if(attacker instanceof Antelope)
            super.collision(attacker, pX, pY, actionOrganisms);
        else
            {
            Random random = new Random();
            if(random.nextInt(2) == 0)
            {
                super.collision(attacker, pX, pY, actionOrganisms);
            }
            else {
                int[] directions = new int[4]; // sprawdzenie dostepnosci na 4 sąsiednich polach
                int available_directions = 0;
                Organism neighbor = null;
                if (this.y > 0)
                {
                    // góra TAK
                    neighbor = this.world.getOrganismsMap()[this.getY() - 1][this.getX()];
                    if (neighbor == null || neighbor== attacker)
                        directions[available_directions++] = 0;
                }
                if (this.y < this.world.getN() - 1)
                {
                    // dół TAK
                    neighbor = this.world.getOrganismsMap()[this.getY() + 1][this.getX()];
                    if (neighbor == null || neighbor == attacker)
                        directions[available_directions++] = 1;
                }
                if (this.getX() > 0)
                {
                    //lewo TAK
                    neighbor = this.world.getOrganismsMap()[this.getY()][this.getX() - 1];
                    if (neighbor == null || neighbor == attacker)
                        directions[available_directions++] = 2;
                }
                if (this.getX() < this.world.getM() - 1)
                {
                    //prawo TAK
                    neighbor = this.world.getOrganismsMap()[this.getY()][this.getX() + 1];
                    if (neighbor == null || neighbor == attacker)
                        directions[available_directions++] = 3;
                }

                int direction_index = random.nextInt(available_directions); // wylosowanie kierunku ucieczki i przemieszczenie sie
                if (directions[direction_index] == 0)
                {
                    this.world.swapOrganisms(pX, pY, this.getX(), this.getY(), this.getX(), this.getY() - 1);
                    this.setY(this.getY() - 1);
                    this.world.addComment(this.species + " ucieka przed " + attacker.getSpecies() + " na pole: " + this.getX() + " " + (this.getY()-1));
                }
                if (directions[direction_index] == 1)
                {
                    this.world.swapOrganisms(pX, pY, this.getX(), this.getY(), this.getX(), this.getY() + 1);
                    this.setY(this.getY() + 1);
                    this.world.addComment(this.species + " ucieka przed " + attacker.getSpecies() + " na pole: " + this.getX() + " " + (this.getY()+1));
                }
                if (directions[direction_index] == 2)
                {
                    this.world.swapOrganisms(pX, pY, this.getX(), this.getY(), this.getX() - 1, this.getY());
                    this.setX(this.getX() - 1);
                    this.world.addComment(this.species + " ucieka przed " + attacker.getSpecies() + " na pole: " + (this.getX()-1) + " " + this.getY());
                }
                if (directions[direction_index] == 3)
                {
                    this.world.swapOrganisms(pX, pY, this.getX(), this.getY(), this.getX() + 1, this.getY());
                    this.setX(this.getX() + 1);
                    this.world.addComment(this.species + " ucieka przed " + attacker.getSpecies() + " na pole: " + (this.getX()+1) + " " + this.getY());
                }
            }
        }
    }
}
