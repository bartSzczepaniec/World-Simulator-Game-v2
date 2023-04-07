package worldSimulator;

import java.io.Serializable;
import java.util.List;

public abstract class Organism implements Serializable {

    protected int strength;
    protected String species;
    protected int initiative;
    protected int birthTurn;
    protected int x;
    protected int y;
    protected World world;

    public Organism(int x, int y, World world, int birthRound)
    {
        this.y = y;
        this.x = x;
        this.world = world;
        this.birthTurn = birthRound;
    }

    public abstract void action();

    public abstract void collision(Organism attacker, int pX, int pY, List<Organism> actionOrganisms);

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setStrength(int strength)
    {
        this.strength = strength;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getStrength()
    {
        return strength;
    }

    public int getBirthTurn()
    {
        return birthTurn;
    }

    public String getSpecies()
    {
        return species;
    }
}
