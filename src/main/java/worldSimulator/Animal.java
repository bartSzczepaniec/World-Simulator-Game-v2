package worldSimulator;

import worldSimulator.animals.*;

import java.util.List;
import java.util.Random;

public abstract class Animal extends Organism {

    public Animal(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
    }
    @Override
    public void action()
    {
        int[] directions = new int[4];
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
    }
    @Override
    public void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms)
    {
        if(this.species.equals(attacker.species))
        {
            attacker.setX(pX);
            attacker.setY(pY);
            if(this.birthTurn != this.world.getTurn())
            {
                int[] directions = new int[8]; // sprawdzenie dostepnosci na 8 sąsiednich polach
                int available_directions = 0;
                Organism neighborhood = null;
                if (this.y > 0)
                {
                    // góra TAK
                    neighborhood = this.world.getOrganismsMap()[this.getY() - 1][this.getX()];
                    if (neighborhood == null )
                        directions[available_directions++] = 0;
                }
                if (this.y < this.world.getN() - 1)
                {
                    // dół TAK
                    neighborhood = this.world.getOrganismsMap()[this.getY() + 1][this.getX()];
                    if (neighborhood == null )
                        directions[available_directions++] = 1;
                }
                if (this.getX() > 0)
                {
                    //lewo TAK
                    neighborhood = this.world.getOrganismsMap()[this.getY()][this.getX() - 1];
                    if (neighborhood == null )
                        directions[available_directions++] = 2;
                }
                if (this.getX() < this.world.getM() - 1)
                {
                    //prawo TAK
                    neighborhood = this.world.getOrganismsMap()[this.getY()][this.getX() + 1];
                    if (neighborhood == null )
                        directions[available_directions++] = 3;
                }
                if (attacker.y > 0)
                {
                    // góra TAK
                    neighborhood = this.world.getOrganismsMap()[attacker.getY() - 1][attacker.getX()];
                    if (neighborhood == null )
                        directions[available_directions++] = 4;
                }
                if (attacker.y < attacker.world.getN() - 1)
                {
                    // dół TAK
                    neighborhood = attacker.world.getOrganismsMap()[attacker.getY() + 1][attacker.getX()];
                    if (neighborhood == null )
                        directions[available_directions++] = 5;
                }
                if (attacker.getX() > 0)
                {
                    //lewo TAK
                    neighborhood = attacker.world.getOrganismsMap()[attacker.getY()][attacker.getX() - 1];
                    if (neighborhood == null)
                        directions[available_directions++] = 6;
                }
                if (attacker.getX() < attacker.world.getM() - 1)
                {
                    //prawo TAK
                    neighborhood = attacker.world.getOrganismsMap()[attacker.getY()][attacker.getX() + 1];
                    if (neighborhood == null)
                        directions[available_directions++] = 7;
                }
                if (available_directions == 0)
                    return;
                else // jesli sa wolne pola, to na losowo wybranym tworzony jest nowy organizm tego gatunku
                {
                    Random random = new Random();
                    int direction_index = random.nextInt(available_directions);
                    int newx, newy;
                    newx = newy = -1;
                    if (directions[direction_index] == 0)
                    {
                        newx = this.getX();
                        newy = this.getY() - 1;
                    }
                    else if (directions[direction_index] == 1)
                    {
                        newx = this.getX();
                        newy = this.getY() + 1;
                    }
                    else if (directions[direction_index] == 2)
                    {
                        newx = this.getX() - 1;
                        newy = this.getY();
                    }
                    else if (directions[direction_index] == 3)
                    {
                        newx = this.getX() + 1;
                        newy = this.getY();
                    }
                    else if (directions[direction_index] == 4)
                    {
                        newx = attacker.getX();
                        newy = attacker.getY() - 1;
                    }
                    else if (directions[direction_index] == 5)
                    {
                        newx = attacker.getX();
                        newy = attacker.getY() + 1;
                    }
                    else if (directions[direction_index] == 6)
                    {
                        newx = attacker.getX() - 1;
                        newy = attacker.getY();
                    }
                    else if (directions[direction_index] == 7)
                    {
                        newx = attacker.getX() + 1;
                        newy = attacker.getY();
                    }

                    Animal animal = null;
                    if (this instanceof Wolf)
                    {
                        animal = new Wolf(newx, newy, this.world, this.world.getTurn());

                    }
                    if (this instanceof Sheep)
                    {
                        animal = new Sheep(newx, newy, this.world, this.world.getTurn());
                    }
                    if (this instanceof Turtle)
                    {
                        animal = new Turtle(newx, newy, this.world, this.world.getTurn());
                    }
                    if (this instanceof Antelope)
                    {
                        animal = new Antelope(newx, newy, this.world, this.world.getTurn());
                    }
                    if (this instanceof Fox)
                    {
                        animal = new Fox(newx, newy, this.world, this.world.getTurn());
                    }
                    if (this instanceof CyberSheep)
                    {
                        animal = new CyberSheep(newx, newy, this.world, this.world.getTurn());
                    }
                    this.world.putOrganism(animal, newx, newy);
                    this.world.addComment(this.species + " rozmnaza sie na pole: " + newx + " " + newy);
                }
            }
        }
        else if (attacker.getStrength() < this.strength)
        { // napastnik przegrywa
            this.world.addComment(this.species + " zabija w obronie " + attacker.species + " na polu: " + this.x + " " + this.y);
            this.world.removeOrganism(pX,pY);
        }
        else // napastnik wygrywa
        {
            actionOrganisms.remove(this);
            this.world.addComment(attacker.species + " zabija " + this.species + " na polu: " + this.x + " " + this.y);
            this.world.replaceOrganism(pX,pY,this.x,this.y);
        }
    }
}
