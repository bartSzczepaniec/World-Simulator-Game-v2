import worldSimulator.World;
import worldSimulator.WorldGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {

       // System.out.println("Podaj wymiary planszy (n:m)");
        int n, m;
        n = Integer.parseInt(JOptionPane.showInputDialog("Podaj wysokosc", 20));
        m = Integer.parseInt(JOptionPane.showInputDialog("Podaj szerokosc", 20));
        if(n<=0)
            n=2;
        if(m<=0)
            m=2;
        System.out.println("" + n + " " + m);
        World world = new World(n,m);
        WorldGui worldGui = new WorldGui(world);
    }
}
