package worldSimulator;

import java.io.Serializable;

public class Human extends Animal implements Serializable {
    private int baseStrength;
    private boolean activatedAbility;
    private boolean waiting;
    private int waitingTime;
    private int direction;
    private boolean useAbility;
    public Human(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Czlowiek";
        this.strength = 5;
        this.initiative = 4;
        this.baseStrength = 5;
        this.activatedAbility = false;
        this.waiting = false;
        this.waitingTime = 0;
        this.direction =0;
        this.useAbility =false;
    }

    @Override
    public void action()
    {
        this.world.addComment("Ruch czlowieka ( sila = " + this.strength + " )");
        switch (direction)
        {
            case 1:
                y--;
                break;
            case 2:
                y++;
                break;
            case 3:
                x--;
                break;
            case 4:
                x++;
                break;
        }
        direction = 0;

        if(useAbility)
        {
            if (!activatedAbility && this.strength < 10 && !waiting) // jesli umiejetnosc nie jest aktywna i czas oczekiwania wynosi 0 to można jej uzyc
            {
                useTheAbility();
                System.out.println("Uzyto umiejetnosci");
                world.addComment("Czlowiek uzyl umiejetnosci, jego sila wzrosla do " + strength);
            }
            else
                world.addComment("Czlowiek nie mogl uzyc umiejetnosci ");

            useAbility = false;
        }
    }

    public void useTheAbility()
    {
        baseStrength = strength;
        strength = 10;
        this.activatedAbility = true;
    }

    public void deactivate()
    {
        activatedAbility = false;
        waiting = true;
        waitingTime = 5;
        System.out.println("Umiejetnosc dezaktywowana");
    }

    public void handleAbility()
    {
        if (activatedAbility)
        {
            strength--;
            if (strength == baseStrength)
            {
                deactivate();
            }
        }
        else if (waitingTime > 0 && waiting)
        {
            waitingTime--;
            if (waitingTime == 0)
                waiting =false;
        }
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public boolean isActivatedAbility() {
        return activatedAbility;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public boolean isUseAbility() {
        return useAbility;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setUseAbility(boolean useAbility){
        this.useAbility = useAbility;
    }

    public void setActivatedAbility(boolean activatedAbility) {
        this.activatedAbility = activatedAbility;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
