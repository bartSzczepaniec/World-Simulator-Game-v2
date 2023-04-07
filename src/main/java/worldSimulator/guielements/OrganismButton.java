package worldSimulator.guielements;

import worldSimulator.World;
import worldSimulator.WorldGui;

import javax.swing.*;

public class OrganismButton extends JButton {
    private int oX;
    private int oY;
    private World world;
    private WorldGui worldGui;
    public OrganismButton(ImageIcon imageIcon, int x, int y, World world, WorldGui worldGui)
    {
        super(imageIcon);
        this.oX = x;
        this.oY = y;
        this.world = world;
        this.worldGui = worldGui;
    }

    public World getWorld()
    {
        return world;
    }

    public int getoX()
    {
        return oX;
    }

    public int getoY()
    {
        return oY;
    }

    public WorldGui getWorldGui()
    {
        return worldGui;
    }
}
