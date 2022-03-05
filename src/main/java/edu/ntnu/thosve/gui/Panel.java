package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.Battle;
import edu.ntnu.thosve.formations.Formation;
import edu.ntnu.thosve.units.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * WIP class for the GUI panel. This was for testing purposes only and wil be changed.
 */
public class Panel extends JPanel implements ActionListener {
    private Timer timer;
    private static final int DELAY = 20;

    private long previousTime;
    private double deltaTime;

    private Battle battle;
    private final Army armyOne;
    private final Army armyTwo;
    private final int unitRadius = 4;

    private boolean simulating = false;
    private boolean done = false;

    private BufferedImage background;

    /**
     * Constructor method for creating a new panel which holds the graphics for a battle.
     * @param width  preferred width
     * @param height preferred height
     */
    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        armyOne = new Army("Army One");
        armyTwo = new Army("Army Two");
        battle = new Battle(armyOne, armyTwo);

        setBackground();
        addButtons();
        startGUI();
    }

    /**
     * Method for starting the game loop
     */
    private void startGUI() {
        timer = new Timer(DELAY, this);
        timer.start();
        previousTime = System.nanoTime();

    }

    /**
     * Tries to load the background image file.
     */
    private void setBackground() {
        try {
            background = ImageIO.read(new File("src/main/resources/Field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for getting the armies currently on the panel.
     * @return hashMap with names of armies and the army object
     */
    private HashMap<String, Army> getArmies() {
        HashMap<String, Army> armies = new HashMap<>();
        armies.put(armyOne.getName(), armyOne);
        armies.put(armyTwo.getName(), armyTwo);
        return armies;
    }

    /**
     * Loads the buttons for the panel.
     */
    private void addButtons() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            simulating = true;
        });
        add(startButton);

        JButton addUnitsButton = new JButton("Add Units");
        addUnitsButton.addActionListener(e -> {
            HashMap<String, Army> armies = getArmies();
            AddUnitsForm form = new AddUnitsForm(this, armies);
        });
        add(addUnitsButton);
    }

    /**
     * Method to be called for adding units
     * @param army to add to
     * @param unitType of the units to be added
     * @param name of the units to be added
     * @param numberOfUnits to add
     * @param formation for the units.
     */
    public void addUnits(Army army, UnitTypes unitType, String name, int numberOfUnits, Formation formation) {
        for(int i = 0; i < numberOfUnits; i++) {
            switch (unitType){
                case RANGED_UNIT    -> army.add(new RangedUnit(name));
                case CAVALRY_UNIT   -> army.add(new CavalryUnit(name));
                case INFANTRY_UNIT  -> army.add(new InfantryUnit(name));
                case COMMANDER_UNIT -> army.add(new CommanderUnit(name));
            }
        }
        army.applyFormation(formation);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Method to draw components to the panel.
     * @param g
     */
    public void draw(Graphics g) {
        // --- Background --- //
        g.drawImage(background,0,0,null);

        // --- Army One--- //
        if (armyOne.hasUnits()) {
            g.setColor(Color.GREEN);
            for(Unit unit : armyOne.getAllUnits()) {
                g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
            }
        }

        // --- Army Two--- //
        if (armyTwo.hasUnits()) {
            g.setColor(Color.RED);
            for(Unit unit : armyTwo.getAllUnits()) {
                g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
            }
        }

        // --- Congrats Text --- //
        if (done) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            if (armyOne.hasUnits()) {
                g.drawString(armyOne.getName() + " won!", 50, 500);
            } else {
                g.drawString(armyTwo.getName() + " won!", 500, 500);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        deltaTime = (double) (System.nanoTime() - previousTime) / 1_000_000_000;
        previousTime = System.nanoTime();

        if (simulating) {
            simulating = battle.manualSimulate(deltaTime);
            done = !simulating;
        }
        repaint();
    }
}
