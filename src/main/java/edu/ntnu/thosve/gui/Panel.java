package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.Battle;
import edu.ntnu.thosve.units.InfantryUnit;
import edu.ntnu.thosve.units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    private Timer timer;
    private Battle battle;
    private Army armyOne;
    private Army armyTwo;

    private int unitRadius = 4;

    private boolean simulating = false;

    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        armyOne = new Army("Army One");
        armyTwo = new Army("Army Two");

        for(int i = 0; i < 200; i++) {
            armyOne.add(new InfantryUnit("Infantry 1", 100));
        }

        armyOne.spreadUnitsEvenly(0,200,300, 800);

        for(int i = 0; i < 250; i++) {
            armyTwo.add(new InfantryUnit("Infantry 2", 100));
        }

        armyTwo.spreadUnitsEvenly(500,200,700, 800);

        startGUI();
        addComponents();
    }

    private void addComponents() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle = new Battle(armyOne, armyTwo);
                battle.manualSimulate();
                System.out.println("Boop");
                simulating = true;
            }
        });
        add(startButton);

    }

    public void startGUI() {
        timer = new Timer(120, this);
        timer.start();
        System.out.println("Started");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for(Unit unit : armyOne.getAllUnits()) {
            g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
        }
        g.setColor(Color.RED);
        for(Unit unit : armyTwo.getAllUnits()) {
            g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius, unitRadius);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulating) {
            simulating = battle.manualSimulate();
        }
        repaint();
    }
}
