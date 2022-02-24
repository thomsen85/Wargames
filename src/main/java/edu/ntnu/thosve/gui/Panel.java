package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.Battle;
import edu.ntnu.thosve.formations.Formation;
import edu.ntnu.thosve.formations.RectangleFormation;
import edu.ntnu.thosve.units.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;



public class Panel extends JPanel implements ActionListener, MouseListener {
    private Timer timer;
    private Battle battle;
    private Army armyOne;
    private Army armyTwo;

    private int unitRadius = 4;

    private int zoomFactor;

    private boolean simulating = false;
    private boolean done = false;

    private final int DELAY = 20;

    private Random random;

    BufferedImage background;


    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        random = new Random();

        armyOne = new Army("Army One");
        armyTwo = new Army("Army Two");

        zoomFactor = 2;

        setBackground();
        startGUI();
        addComponents();
        addMouseListener(this);
    }

    private void setBackground() {
        try {
            background = ImageIO.read(new File("src/main/resources/Field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addComponents() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            battle = new Battle(armyOne, armyTwo);
            simulating = true;
        });
        add(startButton);

        JButton addUnitsButton = new JButton("Add Units");
        addUnitsButton.addActionListener(e -> {
            HashMap<String, Army> armies = new HashMap<>();
            armies.put("Army One", armyOne);
            armies.put("Army Two", armyTwo);

            HashMap<String, Class> units = new HashMap<>();
            units.put("Cavalry Unit", CavalryUnit.class);
            units.put("Infantry Unit", InfantryUnit.class);
            units.put("Ranged Unit", RangedUnit.class);
            units.put("Commander Unit", CommanderUnit.class);

            AddUnitsForm form = new AddUnitsForm(this, armies, units);
        });
        add(addUnitsButton);

    }

    public void addUnits(Army army, Class unit, String name, int numberOfUnits, Formation formation) {
        for(int i = 0; i < numberOfUnits; i++) {
            try {
                army.add((Unit) unit.getDeclaredConstructor(String.class).newInstance(name));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        army.applyFormation(formation);
    }

    private void startGUI() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // --- Background ---
        g.drawImage(background,0,0,null);

        if (armyOne.hasUnits()) {
            g.setColor(Color.GREEN);
            for(Unit unit : armyOne.getAllUnits()) {
                g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius * zoomFactor, unitRadius * zoomFactor);
            }
        }

        g.setColor(Color.RED);
        for(Unit unit : armyTwo.getAllUnits()) {
            g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius * zoomFactor, unitRadius * zoomFactor);
        }

        if (done) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            if (armyOne.hasUnits()) {
                g.drawString("Army One won!", 50, 500);
            } else {
                g.drawString("Army Two won", 500, 500);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + ", " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
