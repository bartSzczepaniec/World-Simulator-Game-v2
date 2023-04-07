package worldSimulator;

import worldSimulator.guielements.OrganismsMenu;
import worldSimulator.guielements.OrganismButton;
import worldSimulator.plants.*;
import worldSimulator.animals.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WorldGui implements Serializable {
    private JButton newTurn;
    private JPanel mainPanel;
    private JButton loadButton;
    private JTextArea textArea1;
    private JButton saveButton;
    private JPanel board;
    private JLabel directionLabel;
    private JFrame frame;
    private OrganismButton[][] organismsGUI;
    private World world;
    public WorldGui(World world) {
        frame = new JFrame("Symulator swiata");
        buildWorld(world);
    }

    public void buildWorld(World world) {
        this.world = world;
        int n = world.getN();
        int m = world.getM();

        board.setLayout(new GridLayout(n,m,5,5));

        organismsGUI = new OrganismButton[n][m];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                organismsGUI[i][j] = new OrganismButton(new ImageIcon("src/main/resources/pusto.jpg"),j,i, world,this);
                organismsGUI[i][j].setContentAreaFilled(false);
                organismsGUI[i][j].setBorder(BorderFactory.createEmptyBorder());
                organismsGUI[i][j].addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) { // Tworzenie organizmu na wolnym polu
                                    OrganismButton organismButton = (OrganismButton) e.getSource();
                                    OrganismsMenu organismsMenu = new OrganismsMenu(organismButton.getoX(), organismButton.getoY(), organismButton.getWorld(), organismButton.getWorldGui(), organismButton);
                                    refresh();
                            }
                        }
                );
                board.add(organismsGUI[i][j]);
            }
        }

        mainPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Human human = world.getHuman();
                if (human != null) {
                    if (e.getKeyCode() == KeyEvent.VK_UP)
                    {
                        System.out.println("GORA");
                        if (human.getY() != 0)
                        {
                            human.setDirection(1);
                            directionLabel.setText("Kierunek ruchu w nastepnym ruchu: Gora");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Nie mozna isc do gory", "Nie mozna tam isc!", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                        System.out.println("DOL");
                        if (human.getY() != n-1)
                        {
                            human.setDirection(2);
                            directionLabel.setText("Kierunek ruchu w nastepnym ruchu: Dol");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Nie mozna isc w dol", "Nie mozna tam isc!", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    {
                        System.out.println("LEWO");
                        if (human.getX() != 0)
                        {
                            human.setDirection(3);
                            directionLabel.setText("Kierunek ruchu w nastepnym ruchu: Lewo");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Nie mozna isc w lewo", "Nie mozna tam isc!", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    {
                        System.out.println("PRAWO");
                        if (human.getX() != m-1)
                        {
                            human.setDirection(4);
                            directionLabel.setText("Kierunek ruchu w nastepnym ruchu: Prawo");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Nie mozna isc w prawo", "Nie mozna tam isc!", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_U)
                    {
                        System.out.println("UMIEJETNOSC");
                        human.setUseAbility(true);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        mainPanel.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setFocusable(true);
        frame.setResizable(false);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);


        for( ActionListener al : newTurn.getActionListeners() )
        {
            newTurn.removeActionListener( al );
        }
        for( ActionListener al : loadButton.getActionListeners() )
        {
            loadButton.removeActionListener( al );
        }
        for( ActionListener al : saveButton.getActionListeners() )
        {
            saveButton.removeActionListener( al );
        }
        newTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                directionLabel.setText("Kierunek ruchu w nastepnym ruchu: Brak");
                world.clearComments();
                world.doTurn();
                String comments = "";
                for (String k : world.getComments())
                {
                    comments += k + "\n";
                }
                textArea1.setText(comments);
                refresh();

                mainPanel.requestFocusInWindow(); // !
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWorld();

                mainPanel.requestFocusInWindow(); // !
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWorld();

                mainPanel.requestFocusInWindow(); // !
            }
        });


        this.refresh();
    }

    // odswiezenie wygladu na podstawie nowej tablicy z organizmami
    public void refresh() {
        for(int i = 0; i< organismsGUI.length; i++)
        {
            for(int j = 0; j< organismsGUI[0].length; j++)
            {
                if(world.getOrganismsMap()[i][j]!=null)
                {
                    if(world.getOrganismsMap()[i][j] instanceof Wolf)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/wilk.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources//wilk.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Sheep)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/owca.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/owca.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Fox)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/lis.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/lis.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Turtle)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/zolw.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/zolw.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Antelope)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/antylopa.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/antylopa.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof CyberSheep)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/cyberowca.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/cyberowca.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Grass)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/trawa.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/trawa.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Sonchus)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/mlecz.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/mlecz.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Guarana)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/guarana.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/guarana.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Nightshade)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/wilczejagody.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/wilczejagody.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof SosnowskyHogweed)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/barszczsosnowskiego.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/barszczsosnowskiego.jpg"));
                    }
                    if(world.getOrganismsMap()[i][j] instanceof Human)
                    {
                        organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/czlowiek.jpg"));
                        organismsGUI[i][j].setDisabledIcon(new ImageIcon("src/main/resources/czlowiek.jpg"));
                    }
                    organismsGUI[i][j].setEnabled(false);
                }
                else
                    {
                    organismsGUI[i][j].setIcon(new ImageIcon("src/main/resources/pusto.jpg"));
                    organismsGUI[i][j].setEnabled(true);
                }
            }
        }
        mainPanel.requestFocusInWindow();
    }

    public void saveWorld() {
        String fileName = JOptionPane.showInputDialog(frame, "Podaj nazwe pliku");
        if(fileName == null)
            return;
        else if(fileName.equals(""))
            fileName = "save";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(world);
            outputStream.close();
            fileOutputStream.close();
            System.out.println("Zapisano do pliku");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadWorld() {
        String fileName = JOptionPane.showInputDialog("Podaj nazwe pliku");
        if(fileName == null)
            return;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName + ".txt");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            World world = (World)inputStream.readObject();
            inputStream.close();
            fileInputStream.close();

            board.removeAll();
            textArea1.setText("");
            buildWorld(world);
            System.out.println("Odczytano z pliku");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
