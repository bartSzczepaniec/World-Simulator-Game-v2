package worldSimulator.animals;

import worldSimulator.Animal;
import worldSimulator.World;
import worldSimulator.plants.SosnowskyHogweed;

public class CyberSheep extends Animal {
    public CyberSheep(int x, int y, World world, int birthTurn)
    {
        super(x, y, world, birthTurn);
        this.species = "Cyberowca";
        this.strength = 11;
        this.initiative = 4;
    }

    @Override
    public void action()
    {
        if(this.world.doesHogweedExists())
        {
            SosnowskyHogweed target = this.world.getClosestHogweed(this.x,this.y);
            if(Math.abs(this.x - target.getX()) > Math.abs(this.y - target.getY()))
            {
                if(this.x - target.getX() > 0)
                    this.setX(this.x - 1);
                else
                    this.setX(this.x + 1);
            }
            else
            {
                if(this.y - target.getY() > 0)
                    this.setY(this.y - 1);
                else
                    this.setY(this.y + 1);
            }
        }
        else
        {
            super.action();
        }
    }
}
