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

public class Panel extends JPanel implements ActionListener {
    private Timer timer;
    private final int DELAY = 20;

    private Battle battle;
    private final Army armyOne;
    private final Army armyTwo;
    private final int unitRadius = 4;

    private boolean simulating = false;
    private boolean done = false;

    private BufferedImage background;
    private BufferedImage armyOneIcon;
    private BufferedImage armyTwoIcon;


    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        armyOne = new Army("Ukraine");
        armyTwo = new Army("Russia");
        battle = new Battle(armyOne, armyTwo);

        setBackground();
        loadImages();
        addButtons();
        startGUI();
    }

    private void startGUI() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void loadImages() {
        try {
            armyOneIcon = ImageIO.read(new File("src/main/resources/ukrain.png"));
            armyTwoIcon = ImageIO.read(new File("src/main/resources/russia.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBackground() {
        try {
            //background = ImageIO.read(new File("src/main/resources/Field.png"));
            background = ImageIO.read(new File("src/main/resources/kart.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, UnitTypes> getUnits() {
        HashMap<String, UnitTypes> units = new HashMap<>();
        units.put(UnitTypes.INFANTRY_UNIT.toString(), UnitTypes.INFANTRY_UNIT);
        units.put(UnitTypes.RANGED_UNIT.toString(), UnitTypes.RANGED_UNIT);
        units.put(UnitTypes.CAVALRY_UNIT.toString(), UnitTypes.CAVALRY_UNIT);
        units.put(UnitTypes.COMMANDER_UNIT.toString(), UnitTypes.COMMANDER_UNIT);
        return units;
    }

    private HashMap<String, Army> getArmies() {
        HashMap<String, Army> armies = new HashMap<>();
        armies.put("Ukraine", armyOne);
        armies.put("Russia", armyTwo);
        return armies;
    }

    private void addButtons() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            simulating = true;
        });
        add(startButton);

        JButton addUnitsButton = new JButton("Add Units");
        addUnitsButton.addActionListener(e -> {
            HashMap<String,UnitTypes> units = getUnits();
            HashMap<String, Army> armies = getArmies();
            AddUnitsForm form = new AddUnitsForm(this, armies, units);
        });
        add(addUnitsButton);
    }

    public void addUnits(Army army, UnitTypes unit, String name, int numberOfUnits, Formation formation) {
        for(int i = 0; i < numberOfUnits; i++) {
            switch (unit){
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

    public void draw(Graphics g) {
        // --- Background --- //
        g.drawImage(background,0,0,null);

        // --- Army One--- //
        if (armyOne.hasUnits()) {
            g.setColor(Color.GREEN);
            for(Unit unit : armyOne.getAllUnits()) {
                //g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
                g.drawImage(armyOneIcon, (int) unit.getX(), (int) unit.getY(), null);
            }
        }

        // --- Army Two--- //
        if (armyTwo.hasUnits()) {
            g.setColor(Color.RED);
            for(Unit unit : armyTwo.getAllUnits()) {
                //g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
                g.drawImage(armyTwoIcon, (int) unit.getX(), (int) unit.getY(), null);
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
        if (simulating) {
            simulating = battle.manualSimulate((double) DELAY / 1000);
            done = !simulating;
        }
        repaint();
    }
}
