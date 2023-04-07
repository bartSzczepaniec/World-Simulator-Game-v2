package worldSimulator.guielements;

import worldSimulator.*;
import worldSimulator.plants.*;
import worldSimulator.animals.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OrganismsMenu extends JFrame {
    public OrganismsMenu(int x, int y, World world, WorldGui worldGui, OrganismButton organismButton)
    {
        super("Lista organizmow");
        setSize(200, 400);
        String[] organismNames = new String[12];
        organismNames[0] = "Wilk";
        organismNames[1] = "Owca";
        organismNames[2] = "Lis";
        organismNames[3] = "Zolw";
        organismNames[4] = "Antylopa";
        organismNames[5] = "Cyberowca";
        organismNames[6] = "Czlowiek";
        organismNames[7] = "Trawa";
        organismNames[8] = "Mlecz";
        organismNames[9] = "Guarana";
        organismNames[10] = "Wilczejagody";
        organismNames[11] = "Barszczsosnowskiego";


        JList jList = new JList(organismNames);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(organismButton.isEnabled())
                {
                    if (jList.getSelectedValue().equals("Wilk"))
                    {
                        Wolf organism = new Wolf(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO WILKA");
                    }
                    if (jList.getSelectedValue().equals("Owca"))
                    {
                        Sheep organism = new Sheep(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO WILKA");
                    }
                    if (jList.getSelectedValue().equals("Lis"))
                    {
                        Fox organism = new Fox(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Lis");
                    }
                    if (jList.getSelectedValue().equals("Zolw"))
                    {
                        Turtle organism = new Turtle(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Zolw");
                    }
                    if (jList.getSelectedValue().equals("Antylopa"))
                    {
                        Antelope organism = new Antelope(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Antylopa");
                    }
                    if (jList.getSelectedValue().equals("Cyberowca"))
                    {
                        CyberSheep organism = new CyberSheep(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Cyberowca");
                    }
                    if (jList.getSelectedValue().equals("Czlowiek"))
                    {
                        if (world.getHuman() == null)
                        {
                            Human organism = new Human(x, y, world, world.getTurn()); // TODO JEsli nie istnieje
                            world.addOrganism(organism);
                            System.out.println("STWORZONO Czlowiek");
                        }
                        else
                            {
                            JOptionPane.showMessageDialog(null, "Czlowiek juz istnieje na mapie", "Czlowiek juz istnieje!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (jList.getSelectedValue().equals("Trawa"))
                    {
                        Grass organism = new Grass(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Trawa");
                    }
                    if (jList.getSelectedValue().equals("Mlecz"))
                    {
                        Sonchus organism = new Sonchus(x, y, world, world.getTurn());
                        world.addOrganism(organism);
                        System.out.println("STWORZONO Mlecz");
                    }
                    if (jList.getSelectedValue().equals("Guarana"))
                    {
                        Guarana organizm = new Guarana(x, y, world, world.getTurn());
                        world.addOrganism(organizm);
                        System.out.println("STWORZONO Guarana");
                    }
                    if (jList.getSelectedValue().equals("Wilczejagody"))
                    {
                        Nightshade organizm = new Nightshade(x, y, world, world.getTurn());
                        world.addOrganism(organizm);
                        System.out.println("STWORZONO Wilczejagody");
                    }
                    if (jList.getSelectedValue().equals("Barszczsosnowskiego"))
                    {
                        SosnowskyHogweed organizm = new SosnowskyHogweed(x, y, world, world.getTurn());
                        world.addOrganism(organizm);
                        System.out.println("STWORZONO Barszczsosnowskiego");
                    }
                }
                worldGui.refresh();
                dispose();

            }
        });

        add(jList);
        setVisible(true);

    }
}
