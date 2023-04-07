package worldSimulator;

import worldSimulator.plants.*;
import worldSimulator.animals.*;

import java.io.*;
import java.util.*;

public class World implements Serializable {

    private int n, m, turn; // N - wysokość, M - szerokość
    private Organism[][] organismsMap;
    private List<String> comments;


    public World(int n, int m) {
        this.n = n;
        this.m = m;
        this.turn = 0;
        organismsMap = new Organism[n][m];
        comments = new ArrayList<>();
        Random random = new Random();

        int numberPerSpecies = n * m / 100;
        int numberOfDistinctSpecies = 11;

        int x = random.nextInt(m);
        int y = random.nextInt(n);
        organismsMap[y][x] = new Human(x, y, this, 0);

        for (int i = 0; i < numberOfDistinctSpecies * numberPerSpecies; i++) {

            while (organismsMap[y][x] != null) {
                x = random.nextInt(m);
                y = random.nextInt(n);
            }

            int organismsNumber = i / numberPerSpecies;

            switch (organismsNumber) {
                case 0:
                    // owca
                    organismsMap[y][x] = new Sheep(x, y, this, 0);
                    break;
                case 1:
                    // wilk
                    organismsMap[y][x] = new Wolf(x, y, this, 0);
                    break;
                case 2:
                    // lis
                    organismsMap[y][x] = new Fox(x, y, this, 0);
                    break;
                case 3:
                    // zolw
                    organismsMap[y][x] = new Turtle(x, y, this, 0);
                    break;
                case 4:
                    // antylopa
                    organismsMap[y][x] = new Antelope(x, y, this, 0);
                    break;
                case 5:
                    // cyberowca
                    organismsMap[y][x] = new CyberSheep(x, y, this, 0);
                    break;
                case 6:
                    // trawa
                    organismsMap[y][x] = new Grass(x, y, this, 0);
                    break;
                case 7:
                    // mlecz
                    organismsMap[y][x] = new Sonchus(x, y, this, 0);
                    break;
                case 8:
                    // guarana
                    organismsMap[y][x] = new Guarana(x, y, this, 0);
                    break;
                case 9:
                    // wilcze jagody
                    organismsMap[y][x] = new Nightshade(x, y, this, 0);
                    break;
                case 10:
                    // Barszczsosnowskiego
                    organismsMap[y][x] = new SosnowskyHogweed(x, y, this, 0);
                    break;

            }

        }
    }

    public void doTurn() {
        List<Organism> actionOrganisms = new ArrayList<>();
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                if (organismsMap[i][j] != null)
                    actionOrganisms.add(organismsMap[i][j]);
            }
        }

        for (int i = 0; i < actionOrganisms.size() - 1; i++)
        {
            for (int j = 0; j < actionOrganisms.size() - 1 - i; j++)
            {
                if (actionOrganisms.get(j).initiative < actionOrganisms.get(j + 1).initiative)
                {
                    Collections.swap(actionOrganisms, j, j + 1);
                }
                else if (actionOrganisms.get(j).initiative == actionOrganisms.get(j + 1).initiative)
                {
                    if (actionOrganisms.get(j).birthTurn > actionOrganisms.get(j + 1).birthTurn)
                    {
                        Collections.swap(actionOrganisms, j, j + 1);
                    }
                }
            }
        }

        turn++;
        addComment("Runda nr." + turn);
        while (!actionOrganisms.isEmpty())
        {
            if (actionOrganisms.get(0) != null)
            {
                int pX = actionOrganisms.get(0).x;
                int pY = actionOrganisms.get(0).y;
                actionOrganisms.get(0).action();
                Organism o = actionOrganisms.get(0);

                if (pX != o.x || pY != o.y)
                {
                    if (organismsMap[o.y][o.x] == null)
                    {
                        organismsMap[o.y][o.x] = o;
                        organismsMap[pY][pX] = null;
                    }
                    else
                        { // kolizja
                        organismsMap[o.y][o.x].collision(o, pX, pY, actionOrganisms);
                    }
                }
            }

            actionOrganisms.remove(0);
        }

        Human human = getHuman();
        if (human != null)
            human.handleAbility();

    }

    public void removeOrganism(int x, int y) {
        organismsMap[y][x] = null;
    }

    public void replaceOrganism(int x, int y, int replacedX, int replacedY) {
        organismsMap[replacedY][replacedX] = organismsMap[y][x];
        organismsMap[y][x] = null;
    }

    public void putOrganism(Organism newOrganism, int x, int y) {
        organismsMap[y][x] = newOrganism;
    }

    public void swapOrganisms(int attackerx, int attackery, int escapingx, int escapingy, int targetx, int targety) {
        if (attackerx == targetx && attackery == targety) {
            Organism escaping = organismsMap[escapingy][escapingx];
            organismsMap[escapingy][escapingx] = organismsMap[attackery][attackerx];
            organismsMap[attackery][attackerx] = escaping;
        } else {
            organismsMap[targety][targetx] = organismsMap[escapingy][escapingx];
            organismsMap[escapingy][escapingx] = organismsMap[attackery][attackerx];
            organismsMap[attackery][attackerx] = null;
        }
    }

    public boolean doesHogweedExists() {
        boolean exists = false;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                if (organismsMap[i][j] != null)
                {
                    if (organismsMap[i][j].species == "Barszczsosnowskiego")
                        exists = true;
                }
            }
        }
        return exists;
    }

    public SosnowskyHogweed getClosestHogweed(int x, int y) {
        SosnowskyHogweed closest = null;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                if (organismsMap[i][j] != null)
                {
                    if (organismsMap[i][j] instanceof SosnowskyHogweed)
                    {
                        if (closest == null)
                        {
                            closest = (SosnowskyHogweed) organismsMap[i][j];
                        }
                        else if (Math.abs(x - organismsMap[i][j].getX()) + Math.abs(y - organismsMap[i][j].getY()) < Math.abs(x - closest.getX()) + Math.abs(y - closest.getY()))
                        {
                            closest = (SosnowskyHogweed) organismsMap[i][j];
                        }
                    }
                }
            }
        }
        return closest;
    }

    public void addOrganism(Organism addedOrganism) {
        organismsMap[addedOrganism.getY()][addedOrganism.getX()] = addedOrganism;
    }

    public Human getHuman() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (organismsMap[i][j] instanceof Human) {
                    return (Human) organismsMap[i][j];
                }
            }
        }
        return null;
    }

    public int getNumberOfOrganisms() {
        int numberOfOrganisms = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (organismsMap[i][j] != null)
                    numberOfOrganisms++;
            }
        }
        return numberOfOrganisms;

    }

    public Organism[][] getOrganismsMap() {
        return organismsMap;
    }

    public int getM() {
        return m;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void clearComments() {
        comments.clear();
    }

    public int getN() {
        return n;
    }

    public int getTurn() {
        return turn;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    public void setOrganismsMap(Organism[][] loadedOrganismsMap) {
        organismsMap = null;
        organismsMap = new Organism[n][m];
       for(int i=0;i<n;i++)
       {
           for(int j=0;j<m;j++)
           {
               if(loadedOrganismsMap[i][j] == null)
                   organismsMap[i][j]=null;
               else
               {
                   organismsMap[i][j]=null;
                   organismsMap[i][j] = loadedOrganismsMap[i][j];
               }
           }
       }
    }



}
